#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>
#include <sys/wait.h>
#include <inttypes.h>
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
  int* shmem = create_shared_memory(sizeof(int)*10);
  for (int i = 1; i < 11; i++) {
    shmem[i-1] = i;
  }

  int pipes[2][2];
  pipe(pipes[0]);
  pipe(pipes[1]);

  int pid = fork();

  if (pid == 0) {

    int to_parent_pipe = pipes[1][1];
    int from_parent_pipe = pipes[0][0];
    close(pipes[1][0]);
    close(pipes[0][1]);

    int8_t index = 0;
    int value = 0;

    while (1) {

      scanf("%" SCNd8 " %d", &index, &value);
      printf("index is: %" PRId8 ", value is: %d\n", index, value);

      if (index >= 0) {
        shmem[index] = value;
      }
      

      struct message_header msg_header;
      msg_header.data_len = sizeof(int8_t);
      msg_header.magic = MESSAGE_MAGIC;

      struct message msg = { 0 };
      msg.header = msg_header;
      msg.data[0] = index;

      int send_code = send(to_parent_pipe, &msg);
      printf("send c0de: %d\n", send_code);

      if (index < 0) {
        break;
      }

      fflush(stdout);

    }

    close(to_parent_pipe);
    close(from_parent_pipe);

  } else {

    int from_child_pipe = pipes[1][0];
    int to_child_pipe = pipes[0][1];

    close(pipes[1][1]);
    close(pipes[0][0]);

    struct message msg = {0};

    int8_t index = 0;

    while (1) {
      int read_size = receive(from_child_pipe, &msg);

      for (int i = 0; i < 10; i++) {
        printf("%d ", shmem[i]);
      }
      printf("\n");
      
      index = msg.data[0];
      printf("index: %" PRId8 "\n", index);
      fflush(stdout);

      if (index < 0) {
        break;
      }

    }

  }

}