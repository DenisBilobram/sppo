#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>

#define HEAP_BLOCKS 80
#define BLOCK_CAPACITY 1024

enum block_status { BLK_FREE = 0, BLK_ONE, BLK_FIRST, BLK_CONT, BLK_LAST };

struct heap {
  struct block {
    char contents[BLOCK_CAPACITY];
    enum block_status status;
    struct block** block_mapper;
  } blocks[HEAP_BLOCKS];

  struct block* block_map[HEAP_BLOCKS];
} global_heap = {0};

void add_mapper(struct block* block) {
    for (size_t i = 0; i < HEAP_BLOCKS; i++) {
        if (global_heap.block_map[i] == NULL) {
            global_heap.block_map[i] = block;
            block->block_mapper = &global_heap.block_map[i];
            return;
        }
    }
}

struct block** reserveBlock(size_t count) {

    int start_index = -1;
    int free_count = 0;

    for (size_t i = 0; i < HEAP_BLOCKS; i++) {

        if (global_heap.blocks[i].status != BLK_FREE) {
            start_index = -1;
            free_count = 0;
            continue;
        }

        if (start_index == -1) start_index = i;

        if (++free_count == count) {

            if (count == 1) {
                global_heap.blocks[start_index].status = BLK_ONE;
                
            } else {
                for (size_t j = start_index; j < start_index + count; j++) {
                    global_heap.blocks[j].status = (j == start_index) ? BLK_FIRST : ((j == start_index + count - 1) ? BLK_LAST : BLK_CONT);
                }
            }
            
            add_mapper(&global_heap.blocks[start_index]);
        
            return global_heap.blocks[start_index].block_mapper;
        }

    }
    return NULL;
}

bool freeBlock(struct block* block_p) {
    int index = block_p - global_heap.blocks;

    if (block_p->status == BLK_ONE) {
        block_p->status = BLK_FREE;
        *(block_p->block_mapper) = NULL;
        block_p->block_mapper = NULL;
        return true;
    }

    if (block_p->status != BLK_FIRST) {
        return false;
    }
    
    while (global_heap.blocks[index].status != BLK_LAST) {

        global_heap.blocks[index].status = BLK_FREE;
        if (global_heap.blocks[index].status == BLK_FIRST) {
            *(global_heap.blocks[index].block_mapper) = NULL;
            global_heap.blocks[index].block_mapper = NULL;
        }
        
        index++;
    }
    global_heap.blocks[index].status = BLK_FREE;

    return true;
}

void defragment() {
    size_t write_index = 0;

    for (size_t read_index = 0; read_index < HEAP_BLOCKS; read_index++) {
        if (global_heap.blocks[read_index].status == BLK_FREE) {
            continue;
        }

        size_t sequence_length = 1;

        if (global_heap.blocks[read_index].status == BLK_FIRST) {
            size_t end_of_sequence = read_index;
            while (end_of_sequence < HEAP_BLOCKS && global_heap.blocks[end_of_sequence].status != BLK_LAST) {
                end_of_sequence++;
            }
            sequence_length = (end_of_sequence - read_index) + 1;
        }

        *(global_heap.blocks[read_index].block_mapper) = &global_heap.blocks[write_index];

        memmove(&global_heap.blocks[write_index], &global_heap.blocks[read_index],
                sequence_length * sizeof(struct block));

        write_index += sequence_length;
        read_index += sequence_length - 1;
    }

    // Очистка оставшихся блоков
    for (; write_index < HEAP_BLOCKS; write_index++) {
        global_heap.blocks[write_index].status = BLK_FREE;
        global_heap.blocks[write_index].block_mapper = NULL;
    }
}



int main() {

    struct block** single1 = reserveBlock(1);
    struct block** single2 = reserveBlock(1);
    struct block** single3 = reserveBlock(1);
    struct block** triple1 = reserveBlock(3);
    struct block** single4 = reserveBlock(1);
    struct block** single5 = reserveBlock(1);


    for (size_t i = 0; i < 10; i++) {
        printf("%d ", global_heap.blocks[i].status);
        fflush(stdout);
    }

    printf("\n");
    freeBlock(*single2);
    freeBlock(*single4);

 for (size_t i = 0; i < 10; i++) {
        printf("%d ", global_heap.blocks[i].status);
        fflush(stdout);
    }
    
    printf("\n");

    defragment();

    for (size_t i = 0; i < 10; i++) {
        printf("%d ", global_heap.blocks[i].status);
        fflush(stdout);
    }
    printf("\n");

    freeBlock(*triple1);

    for (size_t i = 0; i < 10; i++) {
        printf("%d ", global_heap.blocks[i].status);
        fflush(stdout);
    }

    defragment();

    for (size_t i = 0; i < 10; i++) {
        printf("%d ", global_heap.blocks[i].status);
        fflush(stdout);
    }

    return 0;
}