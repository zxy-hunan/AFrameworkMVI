package com.xy.mviframework.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @file BaseApp
 * @author zxy
 * @date 2024/7/16 9:21
 * @brief baseApplication
 */
open class BaseApp : Application(), ViewModelStoreOwner {
    private var mAppViewModelStore: ViewModelStore? = null

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
        //autosize
    }

}