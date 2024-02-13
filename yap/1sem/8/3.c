#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>
#include <sys/wait.h>
#include <inttypes.h>
#include <semaphore.h>
#include <fcntl.h>
#include "2.h"

# define MAP_ANONYMOUS	0x20

void* create_shared_memory(size_t size) {
  return mmap(NULL,
              size,
              PROT_READ | PROT_WRITE,
              MAP_SHARED | MAP_ANONYMOUS,
              -1, 0);
}

int main() {
  int* shmem = create_shared_memory(sizeof(int)*11 + sizeof(sem_t));
  for (int i = 1; i < 11; i++) {
    shmem[i-1] = i;
  }

  sem_t* sem = (sem_t*)(shmem + 11);
  sem_init(sem, 1, 0);

  int pid = fork();

  if (pid == 0) {

    int index = 0;
    int value = 0;

    while (1) {

      scanf("%d %d", &index, &value);
      printf("index is: %d, value is: %d\n", index, value);

      shmem[10] = index;
    
      if (index >= 0 && index < 10) {
        shmem[index] = value;
      } else {
        sem_post(sem);
        break;
      }
      
      sem_post(sem);

    }


  } else {

    while (1) {

      sem_wait(sem);

      int index = shmem[10];
      if (index < 0) {
        sem_close(sem);
        break;
      }

      for (int i = 0; i < 10; i++) {
        printf("%d ", shmem[i]);
      }
      printf("\n");

      fflush(stdout);

    }

  }

}