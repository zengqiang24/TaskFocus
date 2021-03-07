//
// Created by 强 zeng on 2/22/21.
//

#include "NativeCrashHandler.h"
#include <jni.h>
#include <string.h>
extern "C" JNIEXPORT jstring JNICALL
Java_com_qzeng_crashwatcher_BugReportManager_stringFromJNI(JNIEnv* env, jobject/*this*/) {
       return env->NewStringUTF("helloWorld");
}