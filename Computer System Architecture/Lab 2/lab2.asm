bits 32

global start        

extern exit   
            
import exit msvcrt.dll    
                          


segment data use32 class=data
    a db 3
    b db 5
    c db 2
    d db 7

segment code use32 class=code
    start:
        ADD al, [a]
        ADD al, [d]
        SUB al, [c]
        ADD cl, [b]
        ADD cl, [b]
        ADD bl, al
        SUB bl, cl
        
        push    dword 0    
        call    [exit]       
    