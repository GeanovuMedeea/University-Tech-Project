bits 32
global start

import printf msvcrt.dll
import exit msvcrt.dll
import scanf msvcrt.dll
extern printf, scanf, exit

extern multiplication

segment data use32
    a dd 0
    b dd 0
    format db "%d", 0
	format_string db "result=%d", 10, 13, 0

segment code use32 public code
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
        push dword format_string
        call [printf]
        add esp, 4*2 

	push 0
	call [exit]
