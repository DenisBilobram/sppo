#define _DEFAULT_SOURCE

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>


#include "mem.h"
#include "mem_internals.h"
#include "util.h"

void test_normal_allocation() {

    void* heap = heap_init(REGION_MIN_SIZE * 2);
    assert(heap != NULL);

    size_t alloc_size = (REGION_MIN_SIZE / 2);

    void* ptr = _malloc(alloc_size);
    assert(ptr != NULL);  

    memset(ptr, 0, alloc_size);

    _free(ptr);
    heap_term();

}

void test_freeing_single_block() {

    void* heap = heap_init(REGION_MIN_SIZE * 4);
    assert(heap != NULL);  

    void* ptr1 = _malloc(100);
    void* ptr2 = _malloc(200);
    void* ptr3 = _malloc(300);
    assert(ptr1 != NULL && ptr2 != NULL && ptr3 != NULL);

    memset(ptr1, 'A', 100);
    memset(ptr2, 'B', 200);
    memset(ptr3, 'C', 300);

    _free(ptr2);


    assert(memcmp(ptr1, "AAAAAAAAAA", 10) == 0);
    assert(memcmp(ptr3, "CCCCCCCCCC", 10) == 0);

    _free(ptr1);
    _free(ptr3);
    heap_term();

}

void test_freeing_two_blocks() {

    void* heap = heap_init(REGION_MIN_SIZE * 4);
    assert(heap != NULL);  

    void* ptr1 = _malloc(100);
    void* ptr2 = _malloc(200);
    void* ptr3 = _malloc(300);
    void* ptr4 = _malloc(400);
    assert(ptr1 != NULL && ptr2 != NULL && ptr3 != NULL && ptr4 != NULL);

    memset(ptr1, 'A', 100);
    memset(ptr2, 'B', 200);
    memset(ptr3, 'C', 300);
    memset(ptr4, 'D', 400);

    _free(ptr2);
    _free(ptr4);

    assert(memcmp(ptr1, "AAAAAAAAAA", 10) == 0);  
    assert(memcmp(ptr3, "CCCCCCCCCC", 10) == 0);

    _free(ptr1);
    _free(ptr3);
    heap_term();

}

void test_memory_expansion() {

    void* heap = heap_init(REGION_MIN_SIZE);
    assert(heap != NULL);

    void* ptr1 = _malloc(REGION_MIN_SIZE);
    memset(ptr1, 'A', REGION_MIN_SIZE);
    void* ptr2 = _malloc(REGION_MIN_SIZE);
    memset(ptr1, 'B', REGION_MIN_SIZE);

    struct block_header* block1 = block_get_header(ptr1);
    struct block_header* block2 = block_get_header(ptr2);

    assert(block1->next == block2);
    assert(blocks_continuous(block1, block2));

    _free(ptr1);
    _free(ptr2);
    heap_term();

}

void test_memory_allocation_in_new_region() {

    void* heap = heap_init(REGION_MIN_SIZE);
    assert(heap != NULL);

    void* busy = mmap(HEAP_START + REGION_MIN_SIZE * 2, 4096, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
    memset(busy, 'C', 4096);

    void* ptr1 = _malloc(REGION_MIN_SIZE);
    memset(ptr1, 'A', REGION_MIN_SIZE);
    void* ptr2 = _malloc(REGION_MIN_SIZE);
    memset(ptr1, 'B', REGION_MIN_SIZE);

    struct block_header* block1 = block_get_header(ptr1);
    struct block_header* block2 = block_get_header(ptr2);

    assert(!blocks_continuous(block1, block2));

}



int main() {
    test_normal_allocation();
    test_freeing_single_block();
    test_freeing_two_blocks();
    test_memory_expansion();
    test_memory_allocation_in_new_region();
}