#include "transformation.h"
#include "image.h"

#include <stdio.h>
#include <stdlib.h>


struct image rotate(struct image const source) {
    
    struct image rotated;
    rotated.width = source.height;
    rotated.height = source.width;

    fflush(stdout);
    rotated.data = malloc(rotated.width * rotated.height * sizeof(struct pixel));

    for (size_t y = 0; y < source.height; ++y) {
        for (size_t x = 0; x < source.width; ++x) {

            size_t newX = source.height - 1 - y;
            size_t newY = x;
            
            rotated.data[newY * rotated.width + newX] = source.data[y * source.width + x];
        }
    }

    return rotated;

}

struct image rotate_by_times(struct image const source, size_t times) {

    struct image current = source;
    struct image rotated;

    for (size_t i = 0; i < times; i++) {
        rotated = rotate(current);
        if (i > 0) {
            free(current.data); 
        }

        current = rotated; 
    }

    return rotated;

}

struct image rotate_by_angle(struct image const source, enum angles angle) {

    struct image rotated;

    size_t rotate_times = 0;

    switch (angle) {
        case ANGLE_0:
        case ANGLE_90:
        case ANGLE_180:
        case ANGLE_270:
        case ANGLE_360:
            rotate_times = ((ANGLE_360 - angle) / 90);
            break;
        case ANGLE_NEG_90:
        case ANGLE_NEG_180:
        case ANGLE_NEG_270:
            rotate_times = (-angle / 90);
    }

    rotated = rotate_by_times(source, rotate_times);

    return rotated;
}
