#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>

int main() {
    int nr[] = {1,2,3,4};
    const char* c2p_fifo = "/tmp/c2p_fifo"; // path for c2p fifo
    const char* p2c_fifo = "/tmp/p2c_fifo"; // path for p2c fifo
    mkfifo(c2p_fifo, 0666); // create c2p fifo
    mkfifo(p2c_fifo, 0666); // create p2c fifo

    if(fork() == 0) { //child process
        int c2p_fd = open(c2p_fifo, O_WRONLY); // open c2p fifo for writing
        int p2c_fd = open(p2c_fifo, O_RDONLY); // open p2c fifo for reading

        nr[0] += nr[1];
        write(c2p_fd, &nr[0], sizeof(nr[0])); // write partial sum in c2p to parent
        read(p2c_fd, &nr[0], sizeof(nr[0])); // read total sum from parent, from p2c

        close(c2p_fd); // close c2p fifo
        close(p2c_fd); // close p2c fifo
        printf("Child: Sum is: %d\n", nr[0]);
        exit(0);
    }

    int c2p_fd = open(c2p_fifo, O_RDONLY); // open c2p fifo for reading
    int p2c_fd = open(p2c_fifo, O_WRONLY); // open p2c fifo for writing
    nr[2] += nr[3]; // compute partial sum in nr[2]
    read(c2p_fd, &nr[0], sizeof(nr[0])); // read partial sum from child process in nr[0]

    nr[0] += nr[2]; // compute total sum in nr[0]
    write(p2c_fd, &nr[0], sizeof(nr[0])); // write total sum to child

    close(c2p_fd); // close c2p fifo
    close(p2c_fd); // close p2c fifo

    int status;
    wait(&status); // wait for child process
    printf("Parent finished\n");

    unlink(c2p_fifo); // delete c2p fifo
    unlink(p2c_fifo); // delete p2c fifo
    return 0;
}
