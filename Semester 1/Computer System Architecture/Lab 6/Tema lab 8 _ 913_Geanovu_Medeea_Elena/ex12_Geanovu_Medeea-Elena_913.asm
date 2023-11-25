; The following code will open a file called "ana.txt" from current folder,
; it will read maximum 100 characters from this file,
; and it will display to the console the number of chars and the text we've read.

; The program will use:
; - the function fopen() to open/create the file
; - the function fread() to read from file
; - the function printf() to display a text
; - the function fclose() to close the created file.

; Because the fopen() call uses the file access mode "r", the file will be open for
; reading. The file must exist, otherwise the fopen() call will fail.
; For details about the file access modes see the section "Theory".

; Any string used by printf() must be null-terminated ('\0').

bits 32

global start

; declare external functions needed by our program
extern exit, fopen, fclose, scanf, fprintf, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    file_name db "ex2.txt", 0   ; filename to be read
    access_mode db "a", 0       ; file access mode:
                                ; r - opens a file for reading. The file must exist.
    file_descriptor dd -1       ; variable to hold the file descriptor
    
    a dd 0
    
    format db "%d", 0
    format2 db "%d ", 0

; our code starts here
segment code use32 class=code
    start:
        ; call fopen() to create the file
        ; fopen() will return a file descriptor in the EAX or 0 in case of error
        ; eax = fopen(file_name, access_mode)
        push dword access_mode   
        push dword file_name
        call [fopen]
        add esp, 4*2                ; clean-up the stack
        
        mov [file_descriptor], eax  ; store the file descriptor returned by fopen
        
        ; check if fopen() has successfully created the file (EAX != 0)
        cmp eax, 0
        je final

        bucla:
      
            push dword a ;read a from console
            push dword format
            call [scanf]
            add esp, 4*2
            
            mov ebx, [a]
            
            cmp ebx, 0
            je final
            
            push dword ebx
            ;push dword format
            ;push dword [file_descriptor]
            push dword format2
            push dword [file_descriptor]
            call [fprintf]
            add esp, 4*3            
            
        jmp bucla

      final:
        ;call fclose() to close the file
        ; fclose(file_descriptor)
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        ; exit(0)
        push dword 0
        call [exit]