// main.c
#include "vector.h"

int main() {
    vector *v = vector_create(5);

    for (int64_t i = 0; i <= 100; i++) {
        vector_push_back(v, i * i);
    }

    vector_print(v, stdout);

    vector_free(v);
    return 0;
}
