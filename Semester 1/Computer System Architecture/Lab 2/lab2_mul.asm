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
    b db 6
    c db 2
    d dw 15

;(d-b*c+b*2)/a
    
; our code starts here
segment code use32 class=code
    start:
        mov cx, [d]
        mov al, [b]
        mul byte[c]
        mov bx, ax
        mov al, 2
        mul byte[b]
        sub cx, bx
        add cx, ax
        mov ax, cx
        mov dl, [a]
        div dl
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
