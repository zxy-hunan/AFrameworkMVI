package com.xy.application.vm

import android.util.Log
import com.xy.application.api.TestService
import com.xy.application.intent.TestIntent
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.default.apiRetrofit

/**
 * @file TestViewModel
 * @author zxy
 * @date 2024/7/15 18:00
 * @brief testvm
 */
class TestViewModel : BaseViewModel<TestIntent>() {
    val apiService: TestService by lazy {
        apiRetrofit.create(TestService::class.java)
    }

    fun testReq() {
        var dftMap = mutableMapOf<String, String>("pageNo" to "1", "pageSize" to "10")
        apiService.articleList(dftMap).HttpCoroutine(onSuccess = {

            Log.e("MainVm", "articleList: onSuccess")
        }, onError = {
            Log.e("MainVm", "articleList: onError")
        })

        /*  viewModelScope.launch(Dispatchers.Default) {
              delay(3000)
              _intent.emitCoroutine(TestIntent.Test("test"))
          }*/
    }
}