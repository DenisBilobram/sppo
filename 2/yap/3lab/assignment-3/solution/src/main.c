#include <stdlib.h>

#include "bmp.h"
#include "image.h"
#include "transformation.h"
#include "util.h"


int main( int argc, char** argv ) {
    (void) argc; (void) argv; // supress 'unused parameters' warning

    if (argc != 4) {
        perror("Usage: <source> <result> <angle>\n");
        return -1;
    }

    const char* url_source = argv[1];
    const char* url_result = argv[2];
    const char* angle = argv[3];

    FILE* file_source = NULL;

    enum file_open_status file_status = util_open_image(url_source, "rb", &file_source);
    if (file_status == OPEN_ERROR) {
        perror("Error while opening file_source.");
        return -1;
    }

    struct image image_source;
    enum read_bmp_status img_status = from_bmp(file_source, &image_source);

    if (img_status != READ_BMP_OK) {
        perror("Error while parsing bmp.");
    }

    FILE* file_result = NULL;
    file_status = util_open_image(url_result, "wb", &file_result);
    if (file_status == OPEN_ERROR) {
        perror("Error while opening file_result.");
        return -1;
    }

    int64_t angle_num = atoi(angle);
    struct image image_result = rotate_by_angle(image_source, (enum angles)angle_num);

    enum write_bmp_status bmp_status = to_bmp(file_result, &image_result);
    if (bmp_status != WRITE_BMP_OK) {
        perror("Error while writing transformed bmp.");
        return -1;
    }

    if (image_source.data != image_result.data) {
        free(image_source.data);
        free(image_result.data);
    } else {
        free(image_source.data);
    }
    

    fclose(file_source);
    fclose(file_result);

    return 0;
}
