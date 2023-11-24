bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 7
    b dw 3
    c dd 8
    d dq 3

;(a+d)-(c-b)+c unsigned
; our code starts here
segment code use32 class=code
    start:
        mov eax,0
        mov edx,0
        mov al,[a]
        mov ecx,0
        mov ebx,[d]
        add eax,ebx
        add edx,ecx
        mov ecx,[c]
        mov ebx,0
        mov bx,[b]
        sub ecx,ebx
        sub eax,ecx
        add eax,[c]
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
