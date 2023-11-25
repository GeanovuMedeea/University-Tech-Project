#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
    FILE* fd;
    int rows, cols, i, j;
    int** m;

    if(argc <= 1) {
        perror("Not enough arguments");
        return 1;
    }

    fd = fopen(argv[1], "r");
    if(fd == NULL) {
        perror("Failed to open input file");
        return 1;
    }

    if(fread(&rows, sizeof(int), 1, fd) <= 0) {
        perror("Could not read the number of rows");
        return 1;
    }

    if(fread(&cols, sizeof(int), 1, fd) <= 0) {
        perror("Could not read the number of columns");
       	return 1;
    }

    m = (int**)malloc(rows*sizeof(int*));
    for(i=0; i<rows; i++) {
        m[i] = (int*)malloc(cols*sizeof(int));
        fread(&m[i], sizeof(int), 1, fd);
        for(j=0; j<cols; j++) {
            printf("%2d" , m[i][j]);
        }
        printf("\n");
    }
    for(i=0; i<rows; i++){
	free(m[i]);
	}
    free(m);

    fclose(fd);
    return 0;
}
