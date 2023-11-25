#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

int sum = 0;
pthread_mutex_t mtx;
pthread_barrier_t bar;
pthread_rwlock_t lck;

typedef struct{
	int *a;
	int id;
	int start, end;
} data;

void* f(void* arg)
{
	data d = *(data*)arg;
	int s = 0;
	for(int i = d.start; i < d.end; i++)
		s+=d.a[i];
	printf("Thread %d wait..\n",d.id);
	pthread_barrier_wait(&bar);
	printf("Thread %d no longet waits...\n",d.id);
//	pthread_mutex_lock(&mtx);
	sum += s;
//	pthread_mutex_unlock(&mtx);
	//printf("Thread %d Start=%d, End=%d and partial sum %d\n",d.id,d.start,d.end, s);
	return NULL;
}

int main()
{
	int p = 4;
	int n = 12;
	int a[n];
	for(int i = 0; i < n; i++)
	{
		a[i] = i;
	}

	pthread_t th[p];
	data th_d[p];
	pthread_mutex_init(&mtx, NULL);
	pthread_barrier_init(&bar, NULL, p);
	pthread_rwlock_init(&lck,NULL);
	for(int i=0; i <p; i++)
	{
		th_d[i].a = a;
		th_d[i].id = i;
		th_d[i].start = (n/p)*i;
		th_d[i].end = (n/p)*(i+1);
		pthread_create(&th[i], NULL, f, (void*)&th_d[i]);
	}
	for(int i = 0; i <p; i++){
		pthread_join(th[i], NULL);
	}
	pthread_mutex_destroy(&mtx);
	pthread_barrier_destroy(&bar);
	pthread_rwlock_destroy(&lck);
	printf("Total sum: %d\n", sum);
	return 0;
}
