#ifndef TRANSFORMATION
#define TRANSFORMATION

enum angles {
    ANGLE_0 = 0,
    ANGLE_90 = 90,
    ANGLE_180 = 180,
    ANGLE_270 = 270,
    ANGLE_360 = 360,
    ANGLE_NEG_90 = -90,
    ANGLE_NEG_180 = -180,
    ANGLE_NEG_270 = -270
};

struct image rotate_by_angle(struct image const source, enum angles angle);

#endif
