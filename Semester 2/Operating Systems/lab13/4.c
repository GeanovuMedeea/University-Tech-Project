#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

pthread_rwlock_t lck;

void* r1(void* a)
{
	sleep(2);
	pthread_rwlock_rdlock(&lck);
	printf("Reading 1\n");
	sleep(4);
	printf("Reading 1 unlocks...\n");
	pthread_rwlock_unlock(&lck);
	return NULL;
}

void* r2(void* a)
{
        sleep(1);
        pthread_rwlock_rdlock(&lck);
        printf("Reading 2\n");
        sleep(4);
	printf("Reading 2 unlocks...\n");
        pthread_rwlock_unlock(&lck);
        return NULL;
}

void* w1(void* a)
{
        sleep(2);
        pthread_rwlock_wrlock(&lck);
        printf("Writing 1\n");
        sleep(1);
	printf("Writer 1 unlocks...\n");
        pthread_rwlock_unlock(&lck);
        return NULL;
}

void* w2(void* a)
{
        sleep(2);
        pthread_rwlock_wrlock(&lck);
        printf("Writing 2\n");
        sleep(1);
	printf("Writer 2 unlocks...\n");
        pthread_rwlock_unlock(&lck);
        return NULL;
}

int main()
{
	pthread_rwlock_init(&lck, NULL);
	pthread_t t1,t2,t3,t4;
	pthread_create(&t1, NULL, r1, NULL);
	pthread_create(&t2, NULL, r2, NULL);
	pthread_create(&t3, NULL, w1, NULL);
	pthread_create(&t4, NULL, w2, NULL);

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	pthread_join(t3, NULL);
	pthread_join(t4, NULL);
	pthread_rwlock_destroy(&lck);
	return 0;
}
