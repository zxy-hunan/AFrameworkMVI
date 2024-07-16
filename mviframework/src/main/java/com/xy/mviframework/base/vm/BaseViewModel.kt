package com.xy.mviframework.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xy.mviframework.network.api.HttpBy
import com.xy.mviframework.network.default.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * @file BaseViewModel
 * @author zxy
 * @date 2024/7/15 10:50
 * @brief BaseViewModel
 */
open class BaseViewModel<I> : ViewModel() {

    val _intent = MutableSharedFlow<I>()
    val intent: SharedFlow<I>
        get() = _intent


    fun <T> MutableSharedFlow<T>.emitCoroutine(intent: T) {
        viewModelScope.launch(Dispatchers.Default) {
            emit(intent)
        }
    }

    protected fun <T> Flow<BaseResponse<T>>.HttpCoroutine(onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit = {}): Job {
        return viewModelScope.launch {
            HttpBy(
                onError = {
//                    _baseIntent.emitCoroutine(BaseIntent.CompletionRefreshOrLoadError(it))
                    onError.invoke(it)
                }, onSuccess = {
//                    _baseIntent.emitCoroutine(BaseIntent.CompletionRefreshOrLoadSuccess())
                    onSuccess.invoke(it)
                }
            )
        }
    }

}