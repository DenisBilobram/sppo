#include <stdbool.h>
#include <stdlib.h>

#define HEAP_BLOCKS 80
#define BLOCK_CAPACITY 1024

struct heap {
  struct block {
    char contents[BLOCK_CAPACITY];
  } blocks[HEAP_BLOCKS];
  bool is_occupied[HEAP_BLOCKS];
} global_heap = {0};

struct block* reserveBlock() {
    for (size_t i = 0; i < HEAP_BLOCKS; i++) {
        if (!global_heap.is_occupied[i]) {
            global_heap.is_occupied[i] = true;
            return &global_heap.blocks[i];
        }
    }
    return NULL;
}

bool freeBlock(struct block* block_p) {
    int index = block_p - global_heap.blocks;
    if (index >= 0 && index < HEAP_BLOCKS) {
        global_heap.is_occupied[index] = false;
        return true;
    }
    return false;
}