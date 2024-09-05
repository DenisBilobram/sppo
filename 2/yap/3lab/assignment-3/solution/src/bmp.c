#include <math.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

#include "bmp.h"
#include "image.h"

struct __attribute__((packed)) bmp_header {
    uint16_t bfType;
    uint32_t bfileSize;
    uint32_t bfReserved;
    uint32_t bOffBits;
    uint32_t biSize;
    uint32_t biWidth;
    uint32_t biHeight;
    uint16_t biPlanes;
    uint16_t biBitCount;
    uint32_t biCompression;
    uint32_t biSizeImage;
    uint32_t biXPelsPerMeter;
    uint32_t biYPelsPerMeter;
    uint32_t biClrUsed;
    uint32_t biClrImportant;
};

const uint16_t BMP_SIGNATURE = 0x4D42;
const uint32_t BMP_PIXEL_SIZE = 24;
const uint32_t BMP_RESERVED = 0;
const uint32_t BMP_SIZE_OF_INFO_HEADER = 40;
const uint16_t BMP_COUNT_OF_COLOR_PLANES = 1;
const uint32_t BMP_COMPRESSION = 0;
const uint32_t BMP_X_PIXELS_PER_METER = 2835;
const uint32_t BMP_Y_PIXELS_PER_METER = 2835;
const uint32_t BMP_USED_COLORS = 0;
const uint32_t BMP_IMPORTANT_COLORS= 0;

enum read_header_status {
    READ_HEADER_OK,
    READ_HEADER_ERROR,
    READ_HEADER_INVALID_SIGNATURE,
    READ_HEADER_INVALID_BITS,
};

enum read_pixels_status {
    READ_PIXELS_OK,
    READ_PIXELS_ERROR
};

enum read_header_status read_bmp_header(FILE* in, struct bmp_header* header) {
    if (fread(header, sizeof(*header), 1, in) != 1) {
        return READ_HEADER_ERROR;
    }
    if (header->bfType != BMP_SIGNATURE) {
        return READ_HEADER_INVALID_SIGNATURE;
    }
    if (header->biBitCount != BMP_PIXEL_SIZE) {
        return READ_HEADER_INVALID_BITS;
    }
    return READ_HEADER_OK;
}

size_t calculate_bmp_padding(uint32_t biWidth) {
    return (4 - (biWidth * BMP_PIXEL_SIZE / 8) % 4);
}

enum read_pixels_status read_bmp_pixels(FILE* in, struct bmp_header header, struct pixel** pixels) {

    fseek(in, header.bOffBits, SEEK_SET);

    size_t padding = calculate_bmp_padding(header.biWidth);
    size_t row_size = header.biWidth * header.biBitCount / 8;
    size_t pixels_mem = row_size * header.biHeight;
    *pixels = malloc(pixels_mem);

    for (size_t i = 0; i < header.biHeight; i++) {
        if (fread(*pixels + i * header.biWidth, row_size, 1, in) != 1) {
            free(*pixels);
            return READ_PIXELS_ERROR;
        }
        if (padding > 0) {
            if (fseek(in, (long)padding, SEEK_CUR) != 0) {
                return READ_PIXELS_ERROR;
            };
        }
    }

    return READ_PIXELS_OK;
    
}

enum read_bmp_status from_bmp(FILE* in, struct image* img) {

    struct bmp_header header;
    enum read_header_status header_status = read_bmp_header(in, &header);
    if (header_status != READ_HEADER_OK) {
        return READ_BMP_INVALID_HEADER;
    }

    enum read_pixels_status pixels_status = read_bmp_pixels(in, header, &(img->data));
    if (pixels_status != READ_PIXELS_OK) {
        return READ_BMP_INVALID_PIXELS;
    }

    img->height = header.biHeight;
    img->width = header.biWidth;
    
    return READ_BMP_OK;
}

struct bmp_header create_bmp_header(struct image const* img) {

    struct bmp_header header;

    size_t row_size = img->width * BMP_PIXEL_SIZE / 8 + calculate_bmp_padding(img->width);
    size_t image_size = row_size * img->height;

    header.bfType = BMP_SIGNATURE;
    header.bfileSize = image_size + sizeof(struct bmp_header);
    header.bfReserved = BMP_RESERVED;
    header.bOffBits = sizeof(struct bmp_header);
    header.biSize = BMP_SIZE_OF_INFO_HEADER;
    header.biWidth = img->width;
    header.biHeight = img->height;
    header.biPlanes = BMP_COUNT_OF_COLOR_PLANES;
    header.biBitCount = BMP_PIXEL_SIZE;
    header.biCompression = BMP_COMPRESSION;
    header.biSizeImage = image_size;
    header.biXPelsPerMeter = BMP_X_PIXELS_PER_METER;
    header.biYPelsPerMeter = BMP_Y_PIXELS_PER_METER;
    header.biClrUsed = BMP_USED_COLORS;
    header.biClrImportant = BMP_IMPORTANT_COLORS;

    return header;
}

bool write_bmp_header(FILE* out, struct bmp_header* header) {
    if (fwrite(header, sizeof(*header), 1, out) != 1) {
        return false;
    }
    return true;
}

bool write_bmp_pixels(FILE* out, struct image const* img) {

    size_t padding_size = calculate_bmp_padding(img->width);
    uint8_t padding[3] = {0};

    size_t row_size = img->width * BMP_PIXEL_SIZE / 8;

    for (size_t i = 0; i < img->height; i++) {

        if (fwrite(img->data + i * img->width, row_size, 1, out) != 1) {
            return false;
        }

        if (padding_size > 0) {
            if (fwrite(padding, 1, padding_size, out) != padding_size) {
                return false;
            }
        }
    }

    return true;
}

enum write_bmp_status to_bmp(FILE* out, struct image const* img) {

    struct bmp_header header = create_bmp_header(img);

    bool write_header_status = write_bmp_header(out, &header);
    bool write_pixels_status = write_bmp_pixels(out, img);

    if (!write_header_status || !write_pixels_status) {
        return WRITE_BMP_ERROR;
    }
    
    return WRITE_BMP_OK;
}
