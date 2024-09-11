#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <stdio.h>
#include <signal.h>

#include "../include/loader.h"

typedef void (*entry_point_t)();

int main( int argc, char** argv ) {

    Elf64_Ehdr header;
    const char* source_elf_file;
    const char* section_name;
    int64_t fd;
    int64_t return_code;
    Elf64_Phdr program_headers[10];
    int64_t entry_addr;
    entry_point_t entry_point;

    (void) argc; (void) argv;

    if (argc != 3) {
        return EINVAL;
    }

    source_elf_file = argv[1];
    section_name = argv[2];
    
    fd = open(source_elf_file, O_RDWR);

    if (fd == -1) {
        return ENOENT;
    }

    if (read(fd, &header, sizeof(header)) != sizeof(header)) {
        close(fd);
        return EIO;
    }

    if (validate_elf_header(&header) != 0) {
        close(fd);
        return EBADF;
    }

    if (read_program_headers(fd, &header, program_headers) != 0) {
        close(fd);
        return EIO;
    }

    return_code = load_elf_segments(fd, program_headers, header.e_phnum);
    if (return_code != 0) {
        close(fd);
        return return_code;
    }

    entry_addr = get_section_addr(fd, &header, section_name);
    if (entry_addr == -1) {
        close(fd);
        return EINVAL;
    }

    close(fd);

    signal(SIGSEGV, handle_sigsegv);
    
    entry_point = (entry_point_t) entry_addr;
    entry_point(); 

    return 0;
}
