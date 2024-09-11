#ifndef LOADER_H
#define LOADER_H

#include <stdint.h>
#include <elf.h>
#include <sys/types.h>



int64_t validate_elf_header(const Elf64_Ehdr *header);

Elf64_Phdr* read_program_headers(int64_t fd, const Elf64_Ehdr *header, Elf64_Phdr *program_headers);

int64_t load_segment(int64_t fd, const Elf64_Phdr *phdr);

int64_t load_elf_segments(int64_t fd, const Elf64_Phdr *program_headers, size_t phdr_count);

int64_t get_section_addr(int64_t fd, const Elf64_Ehdr *header, const char* section_name);

void handle_sigsegv(int sig);

#endif