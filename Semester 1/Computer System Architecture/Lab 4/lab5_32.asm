bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db '1','6','3','1'
    l equ $-s
    d times l-1 db 0

; our code starts here
;A byte string S of length l is given. Obtain the string D of length l-1, such ;that each element of D is the quotient of two consecutive elements of S, i.e. ;D(i) = S(i) / S(i+1).
;S: 1, 6, 3, 1
;D: 0, 2, 3
Example:
segment code use32 class=code
    start:
        mov ecx,l-1
        mov esi,0
        REPEAT:
            mov al,[s+esi]
            mov bl,[s+esi+1]
            div bl
            mov [d+esi],al
            inc esi
        LOOP REPEAT
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
