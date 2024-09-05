%include "lib.inc"
section .text
global find_word

find_word:
    ; rdi Указатель на нуль-терминированную строку.
    ; rsi Указатель на начало словаря.
    push rbp
    push rbx
    mov rbp, rsi
.loop:
    lea rsi, [rbp+8]
    call string_equals
    test rax, rax
    jz .mismatch
    mov rax, rbp
    jmp .end
.mismatch:
    mov rbx, [rbp]
    test rbx, rbx
    jz .notfound
    mov rbp, [rbp]
    jmp .loop
.notfound:
    xor rax, rax
.end:
    pop rbx
    pop rbp
    ret