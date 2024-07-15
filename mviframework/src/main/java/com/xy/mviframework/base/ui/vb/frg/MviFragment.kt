package com.xy.mviframework.base.ui.vb.frg

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.xy.mviframework.base.vm.BaseIntent
import com.xy.mviframework.base.vm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * @file MviFragment
 * @author zxy
 * @date 2024/7/15 11:42
 * @brief MviFragment
 */
abstract class MviFragment<VB : ViewBinding, VM : BaseViewModel<I>, I : BaseIntent>(private val vMCls: Class<VM>) : BaseFrg<VB>() {
    abstract fun observe()

    private lateinit var _viewModel: VM

    protected val viewModel: VM
        get() = _viewModel

    protected val dftIntent: SharedFlow<I>
        get() = viewModel.intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[vMCls]
    }

    override fun init(savedInstanceState: Bundle?) {
        //监听全局intent事件
        observe()
    }

    private var dftIntentJob: Job? = null

    protected fun intentCallBack(callBack: (I) -> Unit) {
        dftIntentJob = lifecycleScope.launch(Dispatchers.Main) {
            dftIntent.collect {
                callBack.invoke(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dftIntentJob?.cancel()
    }


}