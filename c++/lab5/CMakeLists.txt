cmake_minimum_required(VERSION 3.27)
project(lab5)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fopenmp")

add_executable(lab5 main.cpp)
