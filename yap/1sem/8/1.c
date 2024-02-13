#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

void* create_shared_memory(size_t size) {
  return mmap(NULL,
              size,
              PROT_READ | PROT_WRITE,
              MAP_SHARED | MAP_ANONYMOUS,
              -1, 0);
}


int main() {
  int* shmem = create_shared_memory(sizeof(int)*10);
  for (int i = 1; i < 11; i++) {
    shmem[i-1] = i;
  }


  printf("Shared memory at: %p\n" , shmem);
  int pid = fork();

  if (pid == 0) {
    int index;
    int value;

    scanf("%d", &index);
    scanf("%d", &value);

    shmem[index] = value;


  } else {
    wait(NULL);
    for (int i = 0; i < 10; i++) {
      printf("%d ", shmem[i]);
    }
  }
}