#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#define N 5000

int p=4;

struct params{
	int* a;
	int* b;
	int* c;
	int start,end;
};

typedef struct params params;

void* f(void* arg){
	params function_params = *(params*) arg;
	for(int i=function_params.start;i<function_params.end;i++){
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
	int start=0,mod=N%p,end;
	for(int i=0;i<p;i++){
		parameters[i].a = a;
		parameters[i].b = b;
		parameters[i].c = c;
		end=start+N/p;
		if(mod){
			end+=1;
			mod-=1;
		}
		parameters[i].start =start;
		parameters[i].end=end;
		pthread_create(&t[i],NULL,f,(void*) &parameters[i]);
		start = end;
	}
	for(int i=0;i<p;i++){
		pthread_join(t[i],NULL);
	}
	printf("\n Vector a:\n");
	print_vector(a,N);
	printf("\n Vector b:\n");
        print_vector(b,N);
	printf("\n Vector c:\n");
        print_vector(c,N);
        return 0;
}
