#include <stdint.h>
#include <stdio.h>

#define DEFINE_LIST(type)                                                               \
  struct list_##type {                                                                  \
    type value;                                                                         \
    struct list_##type* next;                                                           \
  };                                                                                    \
  void list_##type##_push(struct list_##type* last, struct list_##type* new_item) {     \
    last->next = new_item;                                                              \
  };                                                                                    \
  void list_##type##_print(char* format, struct list_##type* next) {                    \
    do {                                                                                \
        printf(format, next->value);                                                    \
        printf(" ");                                                                    \
        next = next->next;                                                              \
    } while (next != NULL);                                                             \
    printf("\n");                                                                       \
  };

DEFINE_LIST(int64_t)
DEFINE_LIST(double)


int main() {

    struct list_int64_t el1 = {1, NULL};
    struct list_int64_t el2 = {2, NULL};
    struct list_int64_t el3 = {3, NULL};
    list_int64_t_push(&el1, &el2);
    list_int64_t_push(&el2, &el3);
    list_int64_t_print("%d", &el1);

    struct list_double el4 = {1.23, NULL};
    struct list_double el5 = {2.23, NULL};
    struct list_double el6 = {3.23, NULL};
    list_double_push(&el4, &el5);
    list_double_push(&el5, &el6);
    list_double_print("%f", &el4);
}