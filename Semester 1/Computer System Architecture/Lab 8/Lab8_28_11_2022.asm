bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
extern scanf
import scanf msvcrt.dll

extern printf
import printf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 0
    b dd 0
    
    format db "%d", 0

;scanf a
;scanf b
;a * b
;print result





segment code use32 class=code
    start:
        push dword a ;read a from console
        push dword format
        call [scanf]
        add esp, 4*2
        
        push dword b ;read b from console
        push dword format
        call [scanf]
        add esp, 4*2
        
        mov EAX, [a]
        mov EBX, [b]
        mul EBX
 
        
        push eax
        push dword format
        call [printf]
        add esp, 4*2
        
        
        
        
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
