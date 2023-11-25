bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fread, fprintf
import exit msvcrt.dll 
import fopen msvcrt.dll 
import fread msvcrt.dll 
import fclose msvcrt.dll
import fprintf msvcrt.dll

; our data is declared here 
segment data use32 class=data
    nume_fisier db "soare.txt", 0   ; numele fisierului care va fi deschis
    mod_acces db "r", 0             ; modul de deschidere a fisierului; r - pentru scriere. fisierul trebuie sa existe 
    descriptor_fis dd -1            ; variabila in care vom salva descriptorul fisierului - necesar pentru a putea face referire la fisier
    nr_car_citite dd 0              ; variabila in care vom salva numarul de caractere citit din fisier in etapa curenta
    len equ 100                     ; numarul maxim de elemente citite din fisier intr-o etapa
    buffer resb len                 ; sirul in care se va citi textul din fisier
    
    file_name db "c2.txt", 0       ; filename to be read
    access_modecopy db "w", 0           ; file access mode:
                                    ; a - appends to a file. Append data at
                                    ; the end of the file.
    descriptor_fiscopy dd -1

; our code starts here
segment code use32 class=code
    start:
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0                  
        je final
        
        mov [descriptor_fis], eax
        
        
        bucla:
            push dword [descriptor_fis]
            push dword len
            push dword 1
            push dword buffer
            call [fread]
            add esp, 4*4
            
            cmp eax, 0
            
            je cleanup

            mov [nr_car_citite], eax
            
            
            
            push dword mod_acces     
            push dword file_name
            call [fopen]
            add esp, 4*2                ; clean-up the stack

            mov [descriptor_fiscopy], eax  ; store the file descriptor returned by fopen

            ; check if fopen() has successfully created the file (EAX != 0)
            cmp eax, 0
            je final

            ; append the text to file using fprintf()
            ; fprintf(descriptor_fisopy, text)
            push dword [descriptor_fiscopy]
            call [fprintf]
            add esp, 4

            ; call fclose() to close the file
            ; fclose(descriptor_fiscopy)
            push dword [descriptor_fiscopy]
            call [fclose]
            add esp, 4
            
            
            jmp bucla
        
      cleanup:
        push dword [descriptor_fis]
        call [fclose]
        add esp, 4
        
       

      final:  
        ; exit(0)
        push    dword 0      
        call    [exit] 