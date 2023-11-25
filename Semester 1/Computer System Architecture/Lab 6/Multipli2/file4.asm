bits 32                         
segment code use32 public code
global multiplication

multiplication:
	mov eax, [esp + 4]
	mov ebx, [esp + 8]
	mul ebx
    
	ret 4 ; in this case, 4 represents the number of bytes that need to be cleared from the stack (the parameter of the function)