
section .bss
    buffer resb 20


section .text
global _start

_start:
    mov rdi, buffer
    mov rsi, 20
    call read_word
    mov rdi, 0
    mov rax, 60
    syscall


; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    sub rsp, 8
    mov rax, 0
    mov rdi, 0
    mov rsi, rsp
    mov rdx, 1
    syscall
    test rax, rax
    je .empty
    js .empty
    movzx rax, byte[rsp]
    add rsp, 8
    ret
.empty:
    add rsp, 8
    xor rax, rax
    ret

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    ; rdi - адрес буфера
    ; rsi - размер буфера
    push rdi
    mov r13, rdi
    mov r14, rsi
    xor r15, r15
    dec r14  
.skip_whitespace:
    ; Читаем один символ из stdin
    call read_char
    cmp al, 0x20
    je .skip_whitespace
    cmp al, 0x9
    je .skip_whitespace
    cmp al, 0xA
    je .skip_whitespace
    test al, al
    je .word_done
.read_into_buffer:
    test r14, r14
    je .buffer_full
    js .buffer_full
    mov [r13], al
    inc r13        
    dec r14          
    inc r15
    call read_char
    cmp al, 0x20
    je .word_done
    cmp al, 0x9
    je .word_done
    cmp al, 0xA
    je .word_done
    test rax, rax
    je .word_done
    jmp .read_into_buffer
.word_done:
    pop rax
    mov [r13], byte 0
    mov rdx, r15
    ret
.buffer_full:
    pop rax
    xor rax, rax
    ret