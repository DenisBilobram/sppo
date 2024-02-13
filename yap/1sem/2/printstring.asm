; print_string.asm 
section .data
message:
    db  'hello, world!', 10, 0   ; Добавляем 0 в конце строки, чтобы сделать ее нуль-терминированной

section .text
global _start

exit:
    mov  rax, 60
    xor  rdi, rdi          
    syscall

string_length:
    mov  rax, rdi
  .counter:
    cmp  byte [rdi], 0
    je   .end
    inc  rdi
    jmp  .counter
  .end:
    sub  rdi, rax
    mov  rax, rdi
    ret

print_string:
    call string_length         
    mov  rdx, rax              
    mov  rsi, rdi              
    mov  rax, 1                
    mov  rdi, 1                
    syscall                    
    ret

_start:
    mov  rdi, message          ; Загружаем адрес message в rdi
    call print_string          ; Вызываем print_string
    call exit                  ; Затем вызываем exit