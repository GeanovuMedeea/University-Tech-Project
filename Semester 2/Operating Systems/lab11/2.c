#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#define N 10
int p=4;

void* f(void* arg){
	int value = *(int*) arg;
        printf("I am thread: %lu, and revceived value %d \n",(ulong) pthread_self(), value);
        return 0;
}

void print_vector(int* v, int size){
	printf("\n");
	for(int i=0;i<size;i++)
		printf("%d ", v[i]);
	printf("\n");
	return;
}

int main(int argc, char** argv)
{
	int  a[N], b[N], c[N];
	for(int i=0;i<N;i++){
		a[i]=rand()%10;
		b[i]=rand()%10;
	}
	for(int i=0;i<N;i++)
		c[i]=a[i]+b[i];
	printf("Vector a \n");
	print_vector(a, N);
	printf("\nVector b \n");
        print_vector(b, N);
	printf("\nVector c \n");
        print_vector(c, N);

        return 0;
}
