#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(int argc, char** argv){
	printf("%s", "P\n");
	for(int i = 0; i < 3; i++){
		int pid = fork();
		if(pid == 0){
			printf("C%d\n", i);
			exit(0);
		}
		wait(0);
	}
	printf("%s", "Done\n");
	return 0;
}
