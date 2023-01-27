package com.hyunjine.flow_task.common

import android.util.Log

const val TAG: String = "winter"

fun loggerD(methodName: String = "", message: Any?) {
    Log.d(TAG, "Method Name: $methodName / log: $message")
}

fun loggerW(methodName: String = "", exception: Throwable) {
    Log.w(TAG, "Method Name: $methodName", exception)
}

fun loggerE(methodName: String, exception: Throwable) {
    Log.e(TAG, "Method Name: $methodName", exception)
}