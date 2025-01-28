#include <mpi.h>
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>

void workerProcess(int rank) {
    int startRow, endRow, imgCols, imgType;
    MPI_Recv(&startRow, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(&endRow, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(&imgCols, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(&imgType, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

    std::cout << "Worker " << rank << " received start: " << startRow  << ", end: "<< endRow  << ", col: "<<  imgCols  << ", type: "<<  imgType << std::endl;

    int rows = endRow - startRow;
    cv::Mat localImage(rows, imgCols, imgType);
    MPI_Recv(localImage.data, rows * imgCols, MPI_UNSIGNED_CHAR, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);


    std::cout << "Worker " << rank << " received the image data." << std::endl;

    cv::Mat edges;
    cv::Canny(localImage, edges, 100, 200, 3);

    std::vector<cv::Vec2f> localLines;
    cv::HoughLines(edges, localLines, 1, CV_PI / 180, 60);

    int lineCount = static_cast<int>(localLines.size());
    MPI_Send(&lineCount, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    MPI_Send(localLines.data(), lineCount * 2, MPI_FLOAT, 0, 0, MPI_COMM_WORLD);
}
