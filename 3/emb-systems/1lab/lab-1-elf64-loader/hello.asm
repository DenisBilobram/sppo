section .data
    hello_msg db 'Hello, World!', 0xA  ; Сообщение с новой строкой
    hello_len equ $ - hello_msg        ; Длина сообщения

section .text
    global _start

_start:
    ; Системный вызов write (sys_write)
    mov eax, 1            ; sys_write (1)
    mov edi, 1            ; file descriptor (stdout)
    mov rsi, hello_msg    ; адрес сообщения
    mov edx, hello_len    ; длина сообщения
    syscall               ; вызываем системный вызов

    ; Системный вызов exit (sys_exit)
    mov eax, 60           ; sys_exit (60)
    xor edi, edi          ; код возврата 0
    syscall               ; вызываем системный вызов
