cmake_minimum_required(VERSION 3.4.1)

add_library(native-crash-lib SHARED NativeCrashHandler.cpp)
find_library(log-lib log)
target_link_libraries(native-crash-lib ${log-lib})
