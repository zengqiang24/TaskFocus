// ITaskService.aidl
package com.qzeng.focustask.aidl;

// Declare any non-default types here with import statements

interface ITaskService {
    long getCurrentTaskTime();
    void start();
    void pause();
    void reset();
    void registerCallBack();
}
