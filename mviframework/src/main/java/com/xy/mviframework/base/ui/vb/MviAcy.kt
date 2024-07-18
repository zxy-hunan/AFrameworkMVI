package com.xy.mviframework.base.ui.vb

import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.xy.mviframework.base.vm.BaseIntent
import com.xy.mviframework.base.vm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * @file MviAcy
 * @author Administrator
 * @date 2024/7/15 10:49
 * @brief MviActivity
 */
abstract class MviAcy<VB : ViewBinding, VM : BaseViewModel<I>, I : BaseIntent>(
    private val vMCls: Class<VM>,
    titleRes: String? = null,
    @DrawableRes val windowBgRes: Int? = null
) : BaseAcy<VB>(),LoadingState by LoadingStateDelegate(), OnReloadListener {
    abstract fun observe()
    private var dftIntentJob: Job? = null

    private lateinit var _viewModel: VM
    protected val viewModel: VM
        get() = _viewModel

    protected val dftIntent: SharedFlow<I>
        get() = viewModel.intent

    override fun init() {
        super.init()
        windowBgRes?.let {
            window.setBackgroundDrawableResource(it)
        }
        _viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[vMCls]
        //接收baseintent 事件

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.baseIntent.collect {
               when(it){
                   is BaseIntent.ShowLoading ->{
                       showLoadingView()
                   }

                   is BaseIntent.ShowEmpty ->{
                       showEmptyView()
                   }

                   else ->{

                   }

               }
            }
        }

        observe()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)

    }

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