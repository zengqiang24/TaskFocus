// ITaskService.aidl
package com.qzeng.focustask.aidl;
import com.qzeng.focustask.aidl.ICallBack;
interface ITaskService {
    Bundle getCurrentTaskInfo();
    void start(in Bundle task);
    void startTaskQueue();
    void resume();
    void pause();
    void cancel();
    void registerCallBack(ICallBack callback);
    void unRegisterCallback(ICallBack callback);
    Bundle getTaskRegularQueue();

}
