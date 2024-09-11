#include <elf.h>
#include <stdint.h>
#include <errno.h>
#include <unistd.h>
#include <sys/mman.h>
#include <stdio.h>

#define MAP_ANONYMOUS 0x20

int64_t validate_elf_header(Elf64_Ehdr *header) {

    if (header->e_ident[0] == 0x7F && header->e_ident[1] == 'E' &&
        header->e_ident[2] == 'L' && header->e_ident[3] == 'F') {
        return 0;
    }
    return -1;

}

int64_t read_program_headers(int64_t fd, const Elf64_Ehdr *header, Elf64_Phdr *program_headers) {

    size_t size_to_read;

    if (lseek(fd, header->e_phoff, SEEK_SET) == -1) {
        return -1;
    }

    size_to_read = sizeof(Elf64_Phdr) * header->e_phnum;

    if (read(fd, program_headers, size_to_read) != size_to_read) {
        return EIO;
    }

    return 0;

}

void *my_memset(void *ptr, int value, size_t num) {
    size_t i;
    unsigned char *p = (unsigned char *)ptr;
    for (i = 0; i < num; i++) {
        p[i] = (unsigned char)value;
    }
    return ptr;
}

int write_segment(int fd, const Elf64_Phdr *phdr, void *dest) {

    if (lseek(fd, phdr->p_offset, SEEK_SET) == -1) {
        return EIO;
    }

    if (read(fd, dest, phdr->p_filesz) != phdr->p_filesz) {
        return EIO;
    } 
    if (phdr->p_memsz > phdr->p_filesz) {
        size_t size_to_zero = phdr->p_memsz - phdr->p_filesz;
        void *zero_start = (char *)dest + phdr->p_filesz;
        my_memset(zero_start, 0, size_to_zero);
    }

    return 0;
}

int64_t load_segment(int64_t fd, const Elf64_Phdr *phdr) {

    int64_t page_size;
    int64_t p_memsz_aligned;
    void *memory;
    int64_t aligned_vaddr;
    int prot_flags = 0;

    page_size = sysconf(_SC_PAGESIZE);

    aligned_vaddr = phdr->p_vaddr & ~(page_size - 1);

    p_memsz_aligned = ((phdr->p_memsz + (phdr->p_vaddr - aligned_vaddr)) + page_size - 1) & ~(page_size - 1);

    memory = mmap((void *)aligned_vaddr, p_memsz_aligned, PROT_WRITE, MAP_FIXED | MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);

    if (memory == MAP_FAILED) {
        return ENOMEM;
    }

    if (phdr->p_flags & PF_R) {
        prot_flags |= PROT_READ;
    }
    if (phdr->p_flags & PF_W) {
        prot_flags |= PROT_WRITE;
    }
    if (phdr->p_flags & PF_X) {
        prot_flags |= PROT_EXEC;
    }

    if (write_segment(fd, phdr, (void *)phdr->p_vaddr) != 0) {
        return EIO;
    }

    if (mprotect((void *)aligned_vaddr, p_memsz_aligned, prot_flags) == -1) {
        return EACCES;
    }

    return 0;

}

int64_t load_elf_segments(int64_t fd, const Elf64_Phdr *program_headers, size_t phdr_count) {
    size_t i;
    int64_t return_code;
    for (i = 0; i < phdr_count; i++) {
        if (program_headers[i].p_type == PT_LOAD) {
            return_code = load_segment(fd, &(program_headers[i]));
            if (return_code != 0) {
                return return_code;
            }
        }
    }
    
    return 0;

}

int my_strcmp(const char* str1, const char* str2) {
    while (*str1 && (*str1 == *str2)) {
        str1++;
        str2++;
    }
    return *(unsigned char*)str1 - *(unsigned char*)str2;
}

int64_t get_section_addr(int64_t fd, const Elf64_Ehdr *header, const char* section_name) {
    Elf64_Shdr section_header;
    Elf64_Shdr string_table_header;
    char section_name_buffer[256];
    int i;
    const char *current_section_name;


    if (lseek(fd, header->e_shoff, SEEK_SET) == -1) {
        return -1;  
    }

    for (i = 0; i < header->e_shnum; i++) {

        if (read(fd, &section_header, sizeof(Elf64_Shdr)) != sizeof(Elf64_Shdr)) {
            return -1;  
        }

        if (i == header->e_shstrndx) {
            string_table_header = section_header;
        }
    }

    if (lseek(fd, string_table_header.sh_offset, SEEK_SET) == -1) {
        return -1; 
    }

    if (read(fd, section_name_buffer, sizeof(section_name_buffer)) == -1) {
        return -1;  
    }

    if (lseek(fd, header->e_shoff, SEEK_SET) == -1) {
        return -1; 
    }

    for (i = 0; i < header->e_shnum; i++) {
        if (read(fd, &section_header, sizeof(Elf64_Shdr)) != sizeof(Elf64_Shdr)) {
            return -1; 
        }

        current_section_name = &section_name_buffer[section_header.sh_name];
        if (my_strcmp(current_section_name, section_name) == 0) {
            return section_header.sh_addr;
        }
    }

    return -1;
}

void handle_sigsegv(int sig) {
    _exit(22);
}

