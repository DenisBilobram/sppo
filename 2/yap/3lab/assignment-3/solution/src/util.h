#ifndef UTIL
#define UTIL

#include <stdio.h>

enum file_open_status  {
    OPEN_OK = 0,
    OPEN_ERROR
};

enum file_open_status util_open_image(const char* url, const char* mode, FILE** file);

#endif
