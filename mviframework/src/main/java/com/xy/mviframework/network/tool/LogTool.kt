package com.xy.mviframework.network.tool

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.google.gson.GsonBuilder

/**
 * 日志工具
 */
val LOG_TAG by lazy { "LOG_TOOL_TAG" }
var SHOW_LOG = false
val gson by lazy { GsonBuilder().setDateFormat(DateFormat.YMDHMS).create()!! }

fun logI(vararg msg: Any?) {
    if (SHOW_LOG) {
       LogUtils.i(msg)
    }
}

fun logD(vararg msg: Any?) {
    if (SHOW_LOG) {
        LogUtils.d(msg)
    }
}

fun logE(vararg msg: Any?) {
    LogUtils.e(msg)
//    if (SHOW_LOG) {
//    }
}

fun logPrintJson(tag: String, msg: Any?) {
    if (SHOW_LOG) {
        LogUtils.json(tag, msg)
    }
}

fun <T> logPrintObject(tag: String = LOG_TAG, t: T?) {
    if (SHOW_LOG) {
        val msg = gson.toJson(t)
        logPrintJson(tag, msg)
    }
}

fun httpLog(tag: String = LOG_TAG, msg: String) {
    if (SHOW_LOG) {
        Log.i(tag, msg)
    }
}