// ICallBack.aidl
package com.qzeng.focustask.aidl;

// Declare any non-default types here with import statements

interface ICallBack {
    void onTaskStateChanged(in Bundle bundle);
}
