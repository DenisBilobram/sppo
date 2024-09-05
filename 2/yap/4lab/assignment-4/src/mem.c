#define _DEFAULT_SOURCE

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


#include "mem_internals.h"
#include "mem.h"
#include "util.h"

#define BLOCK_MIN_CAPACITY 24

void debug_block(struct block_header* b, const char* fmt, ... );
void debug(const char* fmt, ... );

extern inline block_size size_from_capacity( block_capacity cap );
extern inline block_capacity capacity_from_size( block_size sz );

static bool            block_is_big_enough( size_t query, struct block_header* block ) { return block->capacity.bytes >= query; }
static size_t          pages_count   ( size_t mem )                      { return mem / getpagesize() + ((mem % getpagesize()) > 0); }
static size_t          round_pages   ( size_t mem )                      { return getpagesize() * pages_count( mem ) ; }

void block_init( void* restrict addr, block_size block_sz, void* restrict next ) {
  *((struct block_header*)addr) = (struct block_header) {
    .next = next,
    .capacity = capacity_from_size(block_sz),
    .is_free = true
  };
}

static size_t region_actual_size( size_t query ) { return size_max( round_pages( query ), REGION_MIN_SIZE ); }
static size_t block_actual_capacity( size_t query ) { return size_max(query, BLOCK_MIN_CAPACITY);}

extern inline bool region_is_invalid( const struct region* r );

static void* map_pages(void const* addr, size_t length, int additional_flags) {
  return mmap( (void*) addr, length, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | additional_flags , -1, 0 );
}

/*  аллоцировать регион памяти и инициализировать его блоком */
struct region alloc_region(void const * addr, size_t query) {

    size_t actual_size = region_actual_size(size_from_capacity((block_capacity){.bytes = query}).bytes);
    struct region* region = map_pages(addr, actual_size, MAP_FIXED_NOREPLACE);

    bool extends = true;

    if (region == MAP_FAILED) {

        region = map_pages(addr, actual_size, 0);

        extends = false;

        if (region == MAP_FAILED) {
            return REGION_INVALID;
        }
    }

    block_init(region, (block_size) {.bytes = actual_size}, NULL);

    return (struct region) {.addr = region, .size = actual_size, .extends = extends };
}

void* heap_init( size_t initial ) {
  const struct region region = alloc_region( HEAP_START, initial );
  if ( region_is_invalid(&region) ) return NULL;

  return region.addr;
}

/*  --- Слияние соседних свободных блоков --- */

static void* block_after( struct block_header const* block ) {
  return  (void*) (block->contents + block->capacity.bytes);
}
bool blocks_continuous (
                               struct block_header const* fst,
                               struct block_header const* snd ) {
  return (void*)snd == block_after(fst);
}

static bool mergeable(struct block_header const* restrict fst, struct block_header const* restrict snd) {
  return fst->is_free && snd->is_free && blocks_continuous( fst, snd ) ;
}

static bool try_merge_with_next( struct block_header* block ) {

  if (block->next == NULL || !mergeable(block, block->next)) {
    return false;
  }

  size_t next_block_size = size_from_capacity(block->next->capacity).bytes;
  block->next = block->next->next;
  block->capacity.bytes += next_block_size;

  return true;

}

/*  освободить всю память, выделенную под кучу */
void heap_term() {
  struct block_header* block = HEAP_START;

  while (block != NULL) {

      _free(block->contents);
      block = block->next;

  }

  block = HEAP_START;
  while (block != NULL) {
    
      while (block->next != NULL) {
        if (!try_merge_with_next(block)) {
          break;
        }
      }

      struct block_header* next_block = block->next;
      if (munmap(block, round_pages(size_from_capacity(block->capacity).bytes)) == -1) {
        perror("Error while heap terminating");
      }
      block = next_block;
  }

}

/*  --- Разделение блоков (если найденный свободный блок слишком большой )--- */

static bool block_splittable( struct block_header* restrict block, size_t query) {
  return block->is_free && query + offsetof( struct block_header, contents ) + BLOCK_MIN_CAPACITY <= block->capacity.bytes;
}

static bool split_if_too_big( struct block_header* block, size_t query ) {

  query = block_actual_capacity(query);

  if (!block_splittable(block, query)) {
    return false;
  }

  block_size block_total_size = size_from_capacity(block->capacity);
  block_size new_left_block_size = size_from_capacity((block_capacity){ .bytes = query });

  struct block_header* new_right_block = (struct block_header*)((uint8_t*)block + new_left_block_size.bytes);

  block_size new_rigth_block_size = {.bytes = block_total_size.bytes - new_left_block_size.bytes};

  block->capacity.bytes -= new_rigth_block_size.bytes;

  block_init(new_right_block, new_rigth_block_size, block->next);
  new_right_block->is_free = true;

  block->next = new_right_block;

  return true;

}

/*  --- ... ecли размера кучи хватает --- */

struct block_search_result {
  enum {BSR_FOUND_GOOD_BLOCK, BSR_REACHED_END_NOT_FOUND, BSR_CORRUPTED} type;
  struct block_header* block;
};


static struct block_search_result find_good_or_last  ( struct block_header* restrict block, size_t sz )    {

  while (block != NULL) {

    while (block->next != NULL) {
      if (!try_merge_with_next(block)) {
        break;
      }
    }

    if (block->is_free && block->capacity.bytes >= sz) {
      return (struct block_search_result){ .type = BSR_FOUND_GOOD_BLOCK, .block = block };
    }

    if (block->next == NULL) {
      return (struct block_search_result){ .type = BSR_REACHED_END_NOT_FOUND, .block = block };
    }

    block = block->next;
  }

  return (struct block_search_result){ .type = BSR_CORRUPTED, .block = NULL };
}

/*  Попробовать выделить память в куче начиная с блока `block` не пытаясь расширить кучу
 Можно переиспользовать как только кучу расширили. */
static struct block_search_result try_memalloc_existing ( size_t query, struct block_header* block ) {
  
  size_t block_capacity = block_actual_capacity(query);

  struct block_search_result block_search = find_good_or_last(block, block_capacity);
  if (block_search.type == BSR_REACHED_END_NOT_FOUND || block_search.type == BSR_CORRUPTED) {
    return block_search;
  }

  split_if_too_big(block_search.block, block_capacity);

  block_search.block->is_free = false;

  return block_search;
}

struct block_header* grow_heap( struct block_header* restrict last, size_t query ) {

  struct region* new_region_adr = (struct region*)(block_after(last));

  struct region new_region = alloc_region(new_region_adr, query);

  if (region_is_invalid(&new_region)) {
    return NULL;
  }

  last->next = new_region.addr;

  bool merge = try_merge_with_next(last);

  return (merge ? last : last->next);

}

/*  Реализует основную логику malloc и возвращает заголовок выделенного блока */
 struct block_header* memalloc( size_t query, struct block_header* heap_start) {

  struct block_search_result block_search = try_memalloc_existing(query, heap_start);

  if (block_search.type == BSR_REACHED_END_NOT_FOUND) {
    struct block_header* block = grow_heap(block_search.block, query);
    if (block != NULL) {
      split_if_too_big(block, query);
      block->is_free = false;
    }
    return block;
  }

  if (block_search.type == BSR_CORRUPTED) {
    return NULL;
  }

  return block_search.block;

}

void* _malloc( size_t query ) {
  struct block_header* const addr = memalloc( query, (struct block_header*) HEAP_START );
  if (addr) return addr->contents;
  else return NULL;
}

struct block_header* block_get_header(void* contents) {
  return (struct block_header*) (((uint8_t*)contents)-offsetof(struct block_header, contents));
}

void _free( void* mem ) {
  if (!mem) return ;
  struct block_header* header = block_get_header( mem );
  header->is_free = true;

  while (header->next != NULL) {
      if (!try_merge_with_next(header)) {
        break;
      }
    }
  
}
