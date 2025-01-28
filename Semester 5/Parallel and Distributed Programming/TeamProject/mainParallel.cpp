#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include "parallel/processChunk.cpp"
#include <chrono>

int main(int argc, char* argv[]) {
    auto start = std::chrono::high_resolution_clock::now();

    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " <Image_Path>" << std::endl;
        return 1;
    }

    cv::Mat image = cv::imread(argv[1], cv::IMREAD_COLOR);
    if (image.empty()) {
        std::cerr << "Error: Unable to open the image file!" << std::endl;
        return 1;
    }

    cv::Mat grayImage;
    cv::cvtColor(image, grayImage, cv::COLOR_BGR2GRAY);

    const int numThreads = 3;
    int rowsPerThread = grayImage.rows / numThreads;
    int remainingRows = grayImage.rows % numThreads;

    std::vector<cv::Vec2f> sharedLines;

    std::vector<std::thread> threads;
    for (int t = 0; t < numThreads; ++t) {
        int startRow = t * rowsPerThread;
        int endRow = startRow + rowsPerThread;
        if (t == numThreads - 1) {
            endRow += remainingRows;
        }

        threads.emplace_back(processChunk, t+1, std::ref(grayImage), startRow, endRow, std::ref(sharedLines));
    }

    for (auto& thread : threads) {
        thread.join();
    }

    for (const auto& line : sharedLines) {
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

    std::cout << "Main thread received the complete Hough transformation data. It's saved at output_image.jpg." << std::endl;
    auto end = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
    std::cout << "Time taken by function: "<< duration.count() << " ms" << std::endl;
    cv::imwrite("output_image.jpg", image);
    //cv::imshow("Detected Lines", image);
    cv::waitKey(0);

    return 0;
}
