%include "dict.inc"
%include "lib.inc"

section .rodata
error_key_not_found: db "Error: key wasn't found in dict", 0
error_too_long_str: db "Error: too long key string", 0

section .bss
buffer resb 255

section .data
buffer_size dd 255

%include "word.inc"

section .text
global _start

_start:
    mov rdi, buffer
    mov rsi, [buffer_size]
    call read_word
    test rax, rax
    mov rcx, error_too_long_str
    jz .error
    mov rdi, buffer
    mov rsi, node%[prev_counter]
    call find_word
    test rax, rax
    mov rcx, error_key_not_found
    jz .error
    lea rdi, [rax+8]
    call string_length
    inc rax
    add rdi, rax
    mov rsi, 1
    call print_string
    mov rdi, 0
    call exit
.error:
    mov rdi, rcx
    mov rsi, 2
    call print_string
    mov rdi, 1
    call exit

