#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

int p=4;

void* f(void* arg){
	printf("I am thread: %lu \n",(ulong) pthread_self());
	return 0;
}

int main(int argc, char** argv)
{
	pthread_t t[p];
	for(int i=0; i<p; i++){
		pthread_create(&t[i],NULL,f,NULL);
	}
	printf("Hello world!\n");
	for(int i=0;i<p;i++){
		pthread_join(t[i],NULL);
	}
	return 0;
}
