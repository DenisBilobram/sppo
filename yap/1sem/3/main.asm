

section .data
codes:
    db '0123456789ABCDEF'

section .text
global exit, print_hex
exit:
    mov rax, 60                  
    xor rdi, rdi                 
    syscall                      

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