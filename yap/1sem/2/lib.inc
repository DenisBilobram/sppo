section .text
 
; Принимает указатель на нуль-терминированную строку, возвращает её длину
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

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push rdi
    call string_length   
    pop rdi
    mov  rdx, rax              
    mov  rsi, rdi              
    mov  rax, 1                
    mov  rdi, 1                
    syscall               
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
    movzx rcx, byte [rdi + rdx]    ; загрузить следующий байт строки
    
    test  rcx, rcx               ; проверить на конец строки 
    jz    .end                   ; если достигнут конец строки, завершить


    sub   cl, '0'                ; символ в число
    js    .end
    cmp cl, 9
    ja    .end              ; если символ не цифра, завершить
    
    imul  rax, rax, 10           ; Умножить текущее число на 10 (сдвинуть влево на один разряд)
    add   rax, rcx               ; Прибавить новую цифру к числу
    
    inc   rdx                    ; Увеличить счётчик прочитанных символов
    jmp   loop             ; Перейти к следующему символу
.end:
    ret
   
