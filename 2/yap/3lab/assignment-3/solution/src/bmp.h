#ifndef BMP
#define BMP

#include "image.h"

#include  <stdio.h>

enum read_bmp_status  {
    READ_BMP_OK = 0,
    READ_BMP_INVALID_HEADER,
    READ_BMP_INVALID_PIXELS,
};

enum  write_bmp_status  {
    WRITE_BMP_OK = 0,
    WRITE_BMP_ERROR
  
};

enum read_bmp_status from_bmp(FILE* in, struct image* img);
enum write_bmp_status to_bmp(FILE* out, struct image const* img);

#endif
