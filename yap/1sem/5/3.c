#include <stdint.h>
#include <stdio.h>

#define DEFINE_LIST(type)                                                               \
  struct list_##type {                                                                  \
    type value;                                                                         \
    struct list_##type* next;                                                           \
  };                                                                                    

DEFINE_LIST(int)
DEFINE_LIST(double)

#define list_push(list, el)                                                             \
  _Generic((list),                                                                        \
           struct list_int : int_list_push(&list, &el),                                   \
           struct list_double : double_list_push(&list, &el),                             \
           default : error("Unsupported operation"))

#define list_print(el)                                                                    \
  _Generic((el),                                                                          \
           struct list_int : int_list_print,                                        \
           struct list_double : double_list_print,                                  \
           default : error_e)(&e1)

void int_list_push(struct list_int* last, struct list_int* new) {
    last->next = new;
}

void double_list_push(struct list_double* last, struct list_double* new) {
    last->next = new;
}

void int_list_print(struct list_int* next) {
    do {                                                                                
        printf("%d", next->value);                                                    
        printf(" ");                                                                    
        next = next->next;                                                              
    } while (next != NULL);                                                             
    printf("\n");  
}

void double_list_print(struct list_double* next) {
    do {                                                                                
        printf("%f", next->value);                                                    
        printf(" ");                                                                    
        next = next->next;                                                              
    } while (next != NULL);                                                             
    printf("\n");  
}

void error(const char* str) {
    printf("%s", str);
}

int main() {
    struct list_int el1 = {1, NULL};
    struct list_int el2 = {2, NULL};
    struct list_int el3 = {3, NULL};

    list_push(el1, el2);
    list_push(el2, el3);
    list_print(el1);

    struct list_double el4 = {1.123, NULL};
    struct list_double el5 = {2.123, NULL};
    struct list_double el6 = {3.123, NULL};

    list_push(el4, el5);
    list_push(el5, el6);
    list_print(el4);

    return 0;
}
