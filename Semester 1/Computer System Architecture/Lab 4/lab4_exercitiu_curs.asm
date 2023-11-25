bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 0000000000011011b ;a = 27
    b dw 0000000000001011b ;b = 11
    c dd 0 ; c = 0

; our code starts here
segment code use32 class=code
    start:
        ;the bits 0-4 of C are the same as the bits 11-15 of A
        mov eax,[c]
        mov bx,[a]
        mov cl,11
        shr bx,cl
        and al,bl
        
        ;the bits 5-11 of C have the value 1
        mov eax,[c]
        mov cl,5
        shr eax,cl
        and eax,4294967295
        
        ;the bits 12-15 of C are the same as the bits 8-11 of B
        mov eax,[c]
        mov cl,12
        shr eax,cl
        mov bx,[b]
        mov cl,8
        shr bx,cl
        and al,bl
        
        ;the bits 16-31 of C are the same as the bits of A
        mov eax,[c]
        mov cl,16
        shr eax,cl
        mov bx,[a]
        and ax,bx
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
