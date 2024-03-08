#ifndef VECTOR
#define VECTOR

#include <stdlib.h>
#include <inttypes.h>
#include <stdio.h>

typedef struct vector vector;

vector* vector_create(size_t initial_capacity);
void vector_free(vector *v);
int64_t vector_get(const vector *v, size_t index);
void vector_set(vector *v, size_t index, int64_t value);
void vector_push_back(vector *v, int64_t value);
void vector_resize(vector *v, size_t new_size);
void vector_print(const vector *v, FILE *stream);

#endif