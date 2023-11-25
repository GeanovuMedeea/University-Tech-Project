#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

typedef struct {
    int* L;
    int* U;
    int* numbers;
    int* sizeL;
    int* sizeU;
    int* S;
    int N;
} ThreadData;

pthread_mutex_t mutex;
pthread_barrier_t barrier;

void* f(void* arg) {
    ThreadData* data = (ThreadData*)arg;
    int thread_id = *(data->numbers);
    pthread_barrier_wait(&barrier);
    int number = data->numbers[thread_id];

    if (number % 2 == 0) {
        pthread_mutex_lock(&mutex);
        data->U[*(data->sizeU)] = number;
        *(data->sizeU) += 1;
        pthread_mutex_unlock(&mutex);
    } else {
        pthread_mutex_lock(&mutex);
        data->L[*(data->sizeL)] = number;
        *(data->sizeL) += 1;

        pthread_mutex_unlock(&mutex);
	}
    pthread_mutex_lock(&mutex);
    *(data->S) += number;

    pthread_mutex_unlock(&mutex);
    return NULL;
}

int main() {
    int N;
    printf("Enter the number of threads: ");
    scanf("%d", &N);

    pthread_t threads[N];
    ThreadData data;

    data.L = (int*)malloc(N * sizeof(int));
    data.U = (int*)malloc(N * sizeof(int));
    data.numbers = (int*)malloc(N * sizeof(int));
    data.sizeL = (int*)malloc(sizeof(int));
    data.sizeU = (int*)malloc(sizeof(int));
    data.S = (int*)malloc(sizeof(int));
    data.N = N;

    *(data.sizeL) = 0;
    *(data.sizeU) = 0;
    *(data.S) = 0;

    pthread_mutex_init(&mutex, NULL);
    pthread_barrier_init(&barrier, NULL, N);

    for (int i = 0; i < N; i++) {
        printf("Enter number for thread %d: ", i);
        scanf("%d", &data.numbers[i]);
    }

    for (int i = 0; i < N; i++) {
        pthread_create(&threads[i], NULL, f, &data);
    }

    for (int i = 0; i < N; i++) {
        pthread_join(threads[i], NULL);
    }

    pthread_mutex_destroy(&mutex);
    pthread_barrier_destroy(&barrier);

    printf("Size of L: %d\n", *(data.sizeL));
    printf("Size of U: %d\n", *(data.sizeU));
    printf("S: %d\n", *(data.S));

    free(data.L);
    free(data.U);
    free(data.numbers);
    free(data.sizeL);
    free(data.sizeU);
    free(data.S);

    return 0;
}

