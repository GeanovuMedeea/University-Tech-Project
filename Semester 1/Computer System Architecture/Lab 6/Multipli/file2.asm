%ifndef _MULTIPLICATION_ASM_ ; continue if _FACTORIAL_ASM_ is undefined
%define _MULTIPLICATION_ASM_ ; make sure that it is defined
                        ; otherwise, %include allows only one inclusion

;define the function

multiplication: ; int _stdcall factorial(int n)
    mov eax, [esp + 4]
    mov ebx, [esp + 8]
    
    mul ebx

    ret 4; in this case, 4 represents the number of bytes that need to be cleared from the stack (the parameter of the function)

%endif