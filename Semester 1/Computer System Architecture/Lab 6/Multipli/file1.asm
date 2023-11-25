bits 32

global start       

import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
extern exit, scanf, printf

%include "file2.asm"

segment data use32 class=data
    a dd 0
    b dd 0
    format db "%d", 0
    format_str db "result=%d", 10, 13, 0
    
segment code use32 class=code
    start:
        push dword a
        push dword format
        call [scanf]
        add esp, 4*2
        
        push dword b
        push dword format
        call [scanf]
        add esp, 4*2
        
        push dword [a]
        push dword [b]
        
        call multiplication
        
        push eax
        push dword format_str
        call [printf]
        add esp, 4*2 
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
