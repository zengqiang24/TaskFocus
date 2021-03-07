// ICallBack.aidl
package com.qzeng.focustask.aidl;

// Declare any non-default types here with import statements

interface ICallBack {
    void onProgress(long currentTime);
    void onTaskStateChanged(int type, int state);
}
