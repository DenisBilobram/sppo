%macro create_string 1-*
    %defstr final_string 
    %rep %0-1
        %strcat final_string final_string, %1
        %strcat final_string final_string, ", "
        %rotate 1
    %endrep
    %strcat final_string final_string, %1
    myString db final_string, 0  ; null-terminated string
%endmacro

; Пример использования
section .data
create_string "hello", "another", "world"