// ITaskService.aidl
package com.qzeng.focustask.aidl;
import com.qzeng.focustask.aidl.ICallBack;
interface ITaskService {
    long getCurrentTaskTime();
    void setTaskType(int type);
    void start();
    void pause();
    void reset();
    void registerCallBack(ICallBack callback);
    void unRegisterCallback(ICallBack callback);
}
