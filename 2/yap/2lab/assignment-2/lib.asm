section .text
 
global exit, string_length, print_string, print_char, print_newline, print_uint, print_int, string_equals, read_char, read_word, parse_uint, parse_int, string_copy

; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, 60
    syscall

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax
.counter:
    cmp byte[rdi + rax], 0
    je .end
    inc rax
    jmp .counter
.end:
    ret

; Принимает указатель на нуль-терминированную строку, файловый дескриптор, выводит её 
print_string:
    call string_length
    xchg rdi, rsi
    mov rdx, rax
    mov rax, 1
    syscall
    ret

; Принимает код символа и выводит его в stdout
print_char:
    mov rsi, rsp
    mov rdx, 1
    mov rax, 1
    mov rdi, 1
    syscall
    add rsp, 8
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, '\n'
    jmp print_char

; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    sub rsp, 21
    mov rax, rdi
    mov rcx, 1
    push rbx
    push rbp
    mov rbx, 10
    lea rbp, [rsp + 20]        
.loop:
    xor rdx, rdx
    div rbx
    add dl, '0'
    mov [rbp], dl
    dec rbp
    test rax, rax
    jnz .loop
    mov rdi, rbp
    inc rdi
    call print_string
    add rsp, 21
    pop rbp
    pop rbx
    ret

; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    xor rax, rax
    cmp rdi, 0
    jge print_uint
    push rdi
    mov rdi, '-'
    push rax
    push rsi
    call print_char
    pop rsi
    pop rax
    pop rdi
    neg rdi
    call print_uint
    ret

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
    xor rax, rax
.loop:
    mov al, byte[rdi]
    cmp al, byte[rsi]
    jne .wrong
    test al, al
    je .right
    inc rdi
    inc rsi
    jmp .loop
.right:
    mov rax, 1
    ret
.wrong:
    xor rax, rax
    ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    sub rsp, 8
    mov rax, 0
    mov rdi, 0
    mov rsi, rsp
    mov rdx, 1
    syscall
    test rax, rax
    jle .empty
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
    push r13
    push r14
    push r15
    push rdi
    mov r13, rdi
    mov r14, rsi
    xor r15, r15
    dec r14  
.skip_whitespace:
    ; Читаем один символ из stdin
    call read_char
    cmp al, ` `
    je .skip_whitespace
    cmp al, `\t`
    je .skip_whitespace
    cmp al, `\n`
    je .skip_whitespace
    test al, al
    je .word_done
.read_into_buffer:
    test r14, r14
    jle .buffer_full
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
    pop r15
    pop r14
    pop r13
    ret
.buffer_full:
    pop rax
    xor rax, rax
    pop r15
    pop r14
    pop r13
    ret

 
; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rdx, rdx
    xor rax, rax
    xor rcx, rcx
loop:
    movzx rcx, byte [rdi + rdx]    
    test  rcx, rcx               
    jz    .end                  
    sub   cl, '0'                
    js    .end
    cmp cl, 9
    ja    .end             
    imul  rax, 10          
    add   rax, rcx               
    inc   rdx                    
    jmp   loop             
.end:
    ret

; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    cmp byte[rdi], '-'
    je .isnegative
    jmp parse_uint
.isnegative:
    inc rdi
    call parse_uint
    test rdx, rdx
    je .error
    neg rax
    inc rdx
    ret
.error:
    ret

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    mov rax, rsi
    test rdx, rdx
    je .buffer_full
    mov r8b, [rdi]
    mov [rsi], r8b
    test r8b, r8b
    je .word_done
    dec rdx
    inc rsi
    inc rdi
    jmp string_copy
.word_done:
    sub rsi, rax
    mov rax, rsi
    ret
.buffer_full:
    xor rax, rax
    ret

