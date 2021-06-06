// ITaskService.aidl
package com.qzeng.focustask.aidl;
import com.qzeng.focustask.aidl.ICallBack;
interface ITaskService {
    Bundle getCurrentTaskInfo();
    void start(int type);
    void resume();
    void pause();
    void registerCallBack(ICallBack callback);
    void unRegisterCallback(ICallBack callback);
}
