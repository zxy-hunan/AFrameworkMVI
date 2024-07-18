package com.xy.mviframework.base

import ToolbarViewDelegate
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.drake.statelayout.StateConfig
import com.dylanc.loadingstateview.LoadingStateView
import com.xy.mviframework.R
import com.xy.mviframework.loadingstate.EmptyViewDelegate
import com.xy.mviframework.loadingstate.ErrorViewDelegate
import com.xy.mviframework.loadingstate.LoadingViewDelegate
import okhttp3.Interceptor

/**
 * @file BaseApp
 * @author zxy
 * @date 2024/7/16 9:21
 * @brief baseApplication
 */
open class BaseApp : Application(), ViewModelStoreOwner {
    private var mAppViewModelStore: ViewModelStore? = null
    open var BASEURL = "test"

    open fun getInterceptors(): List<Interceptor> {
        return emptyList()
    }

    companion object {
        lateinit var instance: BaseApp
            private set
    }

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore ?: ViewModelStore()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        //autosize使用副单位

        setStateView()
    }

    /**
     * @author xhj
     * @since 2024/7/17 18:08
     * @des  缺省页配置
     */
    private fun setStateView() {
        LoadingStateView.setViewDelegatePool {
            register(ToolbarViewDelegate(), LoadingViewDelegate(), ErrorViewDelegate(), EmptyViewDelegate())
        }

        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            errorLayout = R.layout.layout_error
            loadingLayout = R.layout.layout_loading

            //全局的Id
            setRetryIds()
        }
    }

}