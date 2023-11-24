#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#define N 10

int p=4;

struct params{
	int* a;
	int* b;
	int* c;
	int current;
};

typedef struct params params;

void* f(void* arg){
	params function_params = *(params*) arg;
	for(int i=function_params.current;i<N;i+=p){
		function_params.c[i] =function_params.a[i]+function_params.b[i];
	}
        return 0;
}

void print_vector(int* v, int size){
	if(size>100)
		return;
	printf("\n");
	for(int i=0;i<size;i++)
		printf("%d ", v[i]);
	printf("\n");
	return;
}

int main(int argc, char** argv)
{
	params parameters[p];
	int a[N],b[N],c[N];
	for(int i=0;i<N;i++){
		a[i]=rand()%10;
		b[i]=rand()%10;
	}
	pthread_t t[p];
	for(int i=0;i<p;i++){
		parameters[i].a = a;
		parameters[i].b = b;
		parameters[i].c = c;
		pthread_create(&t[i],NULL,f,(void*) &parameters[i]);
	}
	for(int i=0;i<p;i++){
		pthread_join(t[i],NULL);
	}
	printf("vector a \n");
	print_vector(a,N);
	printf("\nvector b \n");
        print_vector(b,N);
	printf("\nvector c \n");
        print_vector(c,N);

        return 0;
}
