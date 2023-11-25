bits 32

global start        

extern exit, printf               
import exit msvcrt.dll
import printf msvcrt.dll


segment data use32 class=data
    a dw 3
    b db 4
    e dw $$-$
    
segment code use32 class=code
    start:
        and ax, 0FFFFFFFFh
        push    dword 0
        call    [exit]
