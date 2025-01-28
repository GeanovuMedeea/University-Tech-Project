cmake_minimum_required(VERSION 3.22.1)
project(TeamProject)

set(CMAKE_CXX_STANDARD 17)

# Set OpenCV_DIR to the correct path in WSL
set(OpenCV_DIR "/usr/lib/x86_64-linux-gnu/cmake/opencv4")

# Find OpenCV
find_package(OpenCV REQUIRED)

find_package(MPI REQUIRED)

# Add your executable files
#add_executable(TeamProject mainProcess.cpp)
add_executable(TeamProject mainParallel.cpp)

# Link OpenCV libraries
target_link_libraries(TeamProject ${OpenCV_LIBS} MPI::MPI_CXX)

#add_executable(mainProcess mainProcess.cpp
#        instances/master.cpp
#        instances/worker.cpp
#        parallel/processChunk.cpp
#        mainParallel.cpp)
add_executable(mainParallel mainParallel.cpp
        instances/master.cpp
        instances/worker.cpp
        parallel/processChunk.cpp
        mainParallel.cpp)

# Link OpenCV libraries
#target_link_libraries(mainProcess ${OpenCV_LIBS} MPI::MPI_CXX)
target_link_libraries(mainParallel ${OpenCV_LIBS} MPI::MPI_CXX)
