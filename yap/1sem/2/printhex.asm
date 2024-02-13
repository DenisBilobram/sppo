section .data
codes:
    db '0123456789ABCDEF'

section .text
global _start
_start:
    mov  rdi, 0x1122334455667788
    call print_hex
    mov  rdi, 0x6142354673165643
    call print_hex
    mov  rdi, 0x4213543814623841
    call print_hex

    mov rax, 60                  ; Номер системного вызова sys_exit
    xor rdi, rdi                 ; Устанавливаем rdi в 0 (код возврата)
    syscall                      ; Вызываем системный вызов

print_hex:
    ; Храним шестнадцатеричное значение в rdi
    mov rdx, 1                   
    mov rcx, 64
.loop:
    push rdi                     
    sub rcx, 4                   
    sar rdi, cl                  
    and rdi, 0xf                 

    lea rsi, [codes + rdi]      
    mov rax, 1                   
    mov rdi, 1                   

    push rcx                     
    syscall                      
    pop rcx                      

    pop rdi                      
    test rcx, rcx                
    jnz .loop                    
    ret

    