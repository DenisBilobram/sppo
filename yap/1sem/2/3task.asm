section .data
codes:
    db '0123456789ABCDEF'

section .text
    global _start

_start:
    call main            
    ; exit syscall
    mov rax, 60          ; sys_exit
    xor rdi, rdi         ; exit code 0
    syscall

main:
    sub rsp, 24          
    
    mov word [rsp], 0xaa     
    mov word [rsp + 8], 0xbb 
    mov word [rsp + 16], 0xff
    
    mov rdi, [rsp]            
    call print_hex           
    mov rdi, [rsp + 8]        
    call print_hex           
    mov rdi, [rsp + 16]       
    call print_hex           
    
    add rsp, 24               ; Освобождаем выделенную память
    ret                       
    
print_hex:                   
    ; Храним шестнадцатеричное значение в rdi
    mov rdx, 1                   
    mov rcx, 8
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