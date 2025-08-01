#include "com_codingapi_jni_HelloWord.h"
#include "jni.h"
#include <iostream>

extern void hello();

extern "C" JNIEXPORT void JNICALL Java_com_codingapi_jni_HelloWord_hello(JNIEnv *env, jobject obj) {
    hello();
}
