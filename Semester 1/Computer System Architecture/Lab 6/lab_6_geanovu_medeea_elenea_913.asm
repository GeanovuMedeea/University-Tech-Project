bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    sir db 2,4,2,5,2,2,4,4
    len equ $-sir
    dest times len db 0
    %assign c 0

;Being given a string of bytes, build a string of words which contains in the low bytes of the words the set of distinct characters from the given string and in the high byte of a word it contains the number of occurrences of the low byte of the word in the given byte string.

;Example:
;given the string
;sir DB 2, 4, 2, 5, 2, 2, 4, 4 
;the result will be
;rez DW 0402h, 0304h, 0105h.
    
; our code starts here
segment code use32 class=code
    start:
        mov ESI, sir ; load offset sir_sursa in ESI 2, 4, 2, 5, 2, 2, 4, 4
        mov EDI, dest; load offset sir_dest in EDI
        mov DH, 0
        mov BH, 0
        mov ECX, len ; no of elements in string
        JECXZ end_loop1
        
        while:
            cld
            mov BL, 1
            
            lodsb ;AL = [ESI]
            
            for_already_found_case:
            
            mov CL,BH
            JECXZ start_normal
            
            prepare_EDI_for_check:
                SUB EDI,2
            loop prepare_EDI_for_check
            
            
            mov CL, BH
            string_loop:
                scasb
                scasb
                je already_found
            loop string_loop
            
            
            mov CL, BH
            prepare_EDI_for_normal:
                SUB EDI,2
            loop prepare_EDI_for_normal
            
            mov CL,BH
            prepare_EDI_for_check2:
                ADD EDI,2
            loop prepare_EDI_for_check2
           
            start_normal:
           
            mov DL, AL
            mov ECX, len - 1
            sub CL, DH
            
            string_loop2: ;find number of occurences of an element
                lodsb
                cmp DL,AL
                jne found
                inc BL
                found:
            loop string_loop2
            
            ;add number of occurences and number of an element to destination
            mov ECX,1
            add_initial:
                mov AH, DL ;number
                mov AL, BL ;occurence
                ;add EDI,2
                stosw
                inc BH
            loop add_initial
            
            jmp case_with_new_number
            
            
            already_found: ;put edi back to current point
            
            mov CH,BH
            sub CH,CL
            mov CL,CH
            mov CH,0
            INC CL
            JECXZ case_with_new_number
            
            prepare_EDI_for_normal2:
                ADD EDI,2
            loop prepare_EDI_for_normal2
            
            inc DH ;keeps track of the last searched element
            
            mov ESI, sir
            
            mov CL, DH
            
            sir_again3: ;start from position element searched + 1 at next iteration
                add ESI, 1          
            loop sir_again3
            
            lodsb
            
            cmp DH,len
            jb for_already_found_case
            
            
            case_with_new_number:
        
            
            inc DH ;keeps track of the last searched element
            
            mov ESI, sir
            
            mov CL, DH
            
            sir_again2: ;start from position element searched + 1 at next iteration
                add ESI, 1          
            loop sir_again2
            
            cmp DH,len
            jb while
        
        
        end_loop1:
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
