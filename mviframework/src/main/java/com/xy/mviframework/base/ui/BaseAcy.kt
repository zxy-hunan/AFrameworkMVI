package com.xy.mviframework.base.ui

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.xy.mviframework.util.getNavigationBarsHeight

/**
 * @file BaseAcy
 * @author zxy
 * @date 2024/7/15 9:19
 * @brief BaseActivity
 */
abstract class BaseAcy(val layoutId: Int) : AppCompatActivity(), Back {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(backCall)
        setWindow()
        if (layoutId != -1) setContentView(layoutId) else setContentView(getContentView())
        initSystem(savedInstanceState)
        initView(savedInstanceState)
        createDataObserver()
        bindData()
    }

    abstract fun getContentView(): View
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun createDataObserver()
    abstract fun bindData()
    open fun initSystem(savedInstanceState: Bundle?) {}
    open fun isStatusBarHide(): Boolean {
        return false
    }

    open fun setWindow() {
//false 表示沉浸，true表示不沉浸
        WindowCompat.setDecorFitsSystemWindows(window, !isStatusBarHide().also {
            window.statusBarColor = if (it) Color.TRANSPARENT else Color.WHITE
        })
        WindowCompat.getInsetsController(window, findViewById<FrameLayout>(android.R.id.content).apply {
            post {
                setPadding(0, 0, 0, if (isStatusBarHide()) getNavigationBarsHeight() else 0)
            }
        }).let { controller ->
            controller.show(WindowInsetsCompat.Type.statusBars())
            controller.isAppearanceLightStatusBars = true //true字体黑色,false白色
        }
    }

    private val backCall = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleBack()
        }
    }

    override fun handleBack() {
        finish()
    }
}