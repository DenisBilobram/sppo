%assign uniq_counter 0

%macro colon 2

    %ifdef prev_counter
        node%[uniq_counter]: dq node%[prev_counter]
    %else
        node%[uniq_counter]: dq 0
    %endif
    
    db %1, 0
    %2:

    %assign prev_counter uniq_counter
    %assign uniq_counter uniq_counter+1

%endmacro