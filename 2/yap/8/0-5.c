#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

pthread_mutex_t m;

void bad_print(char *s)
{
  pthread_mutex_lock(&m);
  for (; *s != '\0'; s++)
  {

    fprintf(stdout, "%c", *s);
    fflush(stdout);
    usleep(100);
  }
  pthread_mutex_unlock(&m);
}

void* my_thread(void* arg)
{
  for(int i = 0; i < 10; i++ )
  {
    bad_print((char *)arg);
    sleep(1);
  }
return NULL;
}

int main()
{
  pthread_t t1, t2;
  pthread_mutex_init(&m, NULL);
  pthread_create(&t1, NULL, my_thread, "Hello\n");
  pthread_create(&t2, NULL, my_thread, "world\n");
  pthread_mutex_destroy(&m);
  pthread_exit( NULL );
  return 0;
}
