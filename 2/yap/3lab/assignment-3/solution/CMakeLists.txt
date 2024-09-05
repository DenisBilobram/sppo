file(GLOB_RECURSE sources CONFIGURE_DEPENDS
    src/*.c
    src/*.h
    include/*.h
)

set(SOURCES
    solution/src/bmp.c
    solution/src/image.c
    solution/src/main.c
    solution/src/transformation.c
    solution/src/util.c
)

add_executable(image-transformer ${sources})
target_include_directories(image-transformer PRIVATE src include)
