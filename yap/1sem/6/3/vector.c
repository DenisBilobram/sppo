#include <stdlib.h>
#include <inttypes.h>
#include "vector.h"

struct vector {
    int64_t *data;       // Указатель на элементы вектора
    size_t capacity;     // Вместимость вектора
    size_t size;         // Текущее количество элементов в векторе
};

vector* vector_create(size_t initial_capacity) {
    vector *v = malloc(sizeof(vector));
    v->data = malloc(sizeof(int64_t) * initial_capacity);
    v->capacity = initial_capacity;
    v->size = 0;
    return v;
}

void vector_free(vector *v) {
    free(v->data);
    free(v);
}

int64_t vector_get(const vector *v, size_t index) {
    if (index < v->size) {
        return v->data[index];
    }
    return 0;
}

void vector_set(vector *v, size_t index, int64_t value) {
    if (index < v->size) {
        v->data[index] = value;
    }
}

void vector_push_back(vector *v, int64_t value) {
    if (v->size == v->capacity) {
        v->capacity *= 2;
        v->data = realloc(v->data, sizeof(int64_t) * v->capacity);
    }
    v->data[v->size++] = value;
}

void vector_resize(vector *v, size_t new_size) {
    if (new_size > v->capacity) {
        v->capacity = new_size;
        v->data = realloc(v->data, sizeof(int64_t) * v->capacity);
    }
    v->size = new_size;
}

void vector_print(const vector *v, FILE *stream) {
    for (size_t i = 0; i < v->size; i++) {
        fprintf(stream, "%" PRId64 " ", v->data[i]);
    }
    fprintf(stream, "\n");
}

