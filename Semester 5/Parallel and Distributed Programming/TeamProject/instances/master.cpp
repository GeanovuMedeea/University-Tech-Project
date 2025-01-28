#include <mpi.h>
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <string>

void masterProcess(const std::string& imagePath, int numProcesses) {
    cv::Mat image = cv::imread(imagePath, cv::IMREAD_COLOR);
    if (image.empty()) {
        std::cerr << "Error: Unable to open the image file!" << std::endl;
        MPI_Abort(MPI_COMM_WORLD, 1);
        return;
    }

    cv::Mat grayImage;
    cv::cvtColor(image, grayImage, cv::COLOR_BGR2GRAY);
    cv::GaussianBlur(grayImage, grayImage, cv::Size(5, 5), 1.5);

    int totalRows = grayImage.rows;
    int imgCols = grayImage.cols;
    int imgType = grayImage.type();
    int elemSize = grayImage.elemSize();

    int rowsPerProcess = totalRows / (numProcesses - 1);
    int remainingRows = totalRows % (numProcesses - 1);

    for (int worker = 1; worker < numProcesses; ++worker) {
        int startRow = (worker - 1) * rowsPerProcess;
        int endRow = startRow + rowsPerProcess + (worker == (numProcesses - 1) ? remainingRows : 0);

        MPI_Send(&startRow, 1, MPI_INT, worker, 0, MPI_COMM_WORLD);
        MPI_Send(&endRow, 1, MPI_INT, worker, 0, MPI_COMM_WORLD);
        MPI_Send(&imgCols, 1, MPI_INT, worker, 0, MPI_COMM_WORLD);
        MPI_Send(&imgType, 1, MPI_INT, worker, 0, MPI_COMM_WORLD);
        MPI_Send(grayImage.ptr(startRow), (endRow - startRow) * imgCols * elemSize, MPI_UNSIGNED_CHAR, worker, 0, MPI_COMM_WORLD);
    }

    std::vector<cv::Vec2f> allLines;
    for (int worker = 1; worker < numProcesses; ++worker) {
        int lineCount;
        MPI_Recv(&lineCount, 1, MPI_INT, worker, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        std::vector<cv::Vec2f> localLines(lineCount);
        MPI_Recv(localLines.data(), lineCount * 2, MPI_FLOAT, worker, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        std::cout << "Master received the Hough partial result from Worker " << worker << std::endl;

        allLines.insert(allLines.end(), localLines.begin(), localLines.end());
    }

    for (const auto& line : allLines) {
        float rho = line[0], theta = line[1];
        cv::Point pt1, pt2;
        double a = cos(theta), b = sin(theta);
        double x0 = a * rho, y0 = b * rho;
        pt1.x = cvRound(x0 + 1000 * (-b));
        pt1.y = cvRound(y0 + 1000 * (a));
        pt2.x = cvRound(x0 - 1000 * (-b));
        pt2.y = cvRound(y0 - 1000 * (a));
        cv::line(image, pt1, pt2, cv::Scalar(0, 0, 255), 1, cv::LINE_AA);
    }

    cv::imwrite("output_image.jpg", image);
    std::cout << "Hough transformation saved at output_image.jpg" << std::endl;
}
