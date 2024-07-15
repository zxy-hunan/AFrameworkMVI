package com.xy.mviframework.base.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

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

}