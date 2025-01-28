#include <opencv2/opencv.hpp>
#include <vector>
#include <thread>
#include <mutex>

std::mutex linesMutex;

void processChunk(int nr, const cv::Mat& grayImage, int startRow, int endRow, std::vector<cv::Vec2f>& sharedLines) {
    std::cout << "Thread " << nr << " received the image data, start: "<<startRow <<" end, " << endRow << " and sharedLines object." << std::endl;

    cv::Mat chunk = grayImage(cv::Range(startRow, endRow), cv::Range::all());

    cv::Mat edges;
    cv::Canny(chunk, edges, 100, 200, 3);

    std::vector<cv::Vec2f> localLines;
    cv::HoughLines(edges, localLines, 1, CV_PI / 180, 60);

    std::lock_guard<std::mutex> guard(linesMutex);
    sharedLines.insert(sharedLines.end(), localLines.begin(), localLines.end());
    std::cout << "Thread " << nr << " computed the Hough transformation of its image data." << std::endl;
}
