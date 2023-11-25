bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 3
    b db 5
    c db 3
    d dw 4
;   d+10*a-b*c

; our code starts here
segment code use32 class=code
    start:
        MOV AL, 10
        MOV DX, [d]
        MOV DL, [a]
        MUL DL
        ADD DX, AX
        MOV AL, [b]
        MOV CL, [c]
        MUL CL
        SUB DX, AX 
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
