package com.xy.application.vm

import androidx.lifecycle.viewModelScope
import com.xy.application.api.TestService
import com.xy.application.intent.TestIntent
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.default.apiRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
//        apiService.test("TEST").HttpCoroutine(onSuccess = {
//
//            _intent.emitCoroutine(TestIntent.Test(it))
//
//        }, onError = {
//
//        })

        viewModelScope.launch(Dispatchers.Default) {
            delay(3000)
            _intent.emitCoroutine(TestIntent.Test("test"))
        }
    }
}