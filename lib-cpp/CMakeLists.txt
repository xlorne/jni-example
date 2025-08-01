cmake_minimum_required(VERSION 3.15)
project(lib-cpp)

set(CMAKE_CXX_STANDARD 17)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

include_directories(
        ${PROJECT_SOURCE_DIR}/include
        ${JNI_INCLUDE_DIRS}
)

add_library(hello SHARED
        src/library.cpp
        src/HelloJNI.cpp
)

add_executable(lib-cpp
        src/main.cpp
        src/library.cpp
        src/HelloJNI.cpp
)

set_target_properties(hello PROPERTIES OUTPUT_NAME "hello")
