bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 1000000010001011b
    b dw 0100001000111010b
    c dd 0
    
;problem nr 15 Geanovy Medeea-Elena group 913, semigroup 1
;Given the words A and B, compute the doubleword C as follows:
;the bits 0-2 of C have the value 0
;the bits 3-5 of C have the value 1
;the bits 6-9 of C are the same as the bits 11-14 of A
;the bits 10-15 of C are the same as the bits 1-6 of B
;the bits 16-31 of C have the value 1
;result should be 1111111111111111 011101 0000 111 000b

; our code starts here
segment code use32 class=code
    start:
        mov ebx, 0 ;we compute the result in bx
        mov eax, 0
        
        or ebx, 00000000000000000000000000111000b ; we force the value of bits 3-5 of the result to the value 1
        
        mov ax, [a]
        and eax, 00000000000000000111100000000000b ;we isolate bits 11-14 of A
        mov cl, 5
        ror eax, cl ;we rotate 5 positions to the left
        or ebx, eax ;we add the bits to the result, bits 6-9 of C
        
        mov ax, [b]
        and eax, 00000000000000000000000001111110b ;we isolate bits 1-6 of B
        mov cl, 9
        rol eax, cl ;we rotate 9 positions to the right
        or ebx, eax ;we add the bits to the result, bits 10-15 of C
        
        or ebx, 11111111111111110000000000000000b ; we force the value of bits 31-16 of the result to the value 1
        
        mov [c], ebx ;we move the result from the register bx to the result variable c

        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
