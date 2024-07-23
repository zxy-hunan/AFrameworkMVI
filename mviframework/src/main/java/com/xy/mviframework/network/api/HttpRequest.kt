package com.xy.mviframework.network.api;

import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * @author zxy
 * @file null.java
 * @date 2024/7/16 11:35
 * @brief http request
 */

suspend fun <T> Flow<BaseRes<T>>.HttpBy(
    onStart: () -> Unit = {},
    onError: (Throwable) -> Unit = {},
    onComplete: (isSuccess: Boolean, msg: String) -> Unit = { _: Boolean, _: String -> },
    onCompleteData: (BaseRes<T>) -> Unit = { },
    onSuccess: (T) -> Unit = {},
    onFail: (msg: String) -> Unit = {},
) = flowOn(Dispatchers.IO)//指定Flow流在Dispatchers.IO线程上运行，用于执行耗时的IO操作。
    .onStart {//在流开始时执行的代码块
        onStart.invoke()
        //可弹出加载进度条
        /*withContext(Dispatchers.Main){

        }*/
    }.catch {
        onError.invoke(it)
        onComplete.invoke(false, it.message ?: "")

    }.onCompletion {//在流完成时执行的代码块
        //可关闭加载进度条

    }.collect {//收集流中的数据
        if (it.code == HttpCode.SUCCEED.code) {
            if (it.data != null) {
                onSuccess.invoke(it.data)
            } else {
                it.rows?.let {onSuccess.invoke(it)  }
                onFail.invoke(it.msg)
            }
            onComplete.invoke(true, it.msg ?: "")
        } else {
            onFail.invoke(it.msg)
        }
        onCompleteData.invoke(it)
        onComplete.invoke(false, it.msg ?: "")
    }


sealed class HttpCode constructor(val code: String) {
    object SUCCEED : HttpCode("200")
}