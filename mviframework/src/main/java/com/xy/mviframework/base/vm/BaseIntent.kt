package com.xy.mviframework.base.vm

/**
 * @file BaseIntent
 * @author zxy
 * @date 2024/7/15 11:02
 * @brief BaseIntent
 */
open class BaseIntent {
    data class ShowLoading(val msg: String = "Loading...") : BaseIntent()

    data class ShowEmpty(val msg: String = "empty") :BaseIntent()

    data class ShowError(val msg: String = "error") :BaseIntent()

    data class ShowLoadingDialog(val msg: String = "loading", val cancelable: Boolean = false, val callback: () -> Unit = {}) : BaseIntent()

    data object DismissLoadingDialog : BaseIntent()
}