package com.xy.mviframework.base.ui.vb

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate

/**
 * @file BaseAcy
 * @author zxy
 * @date 2024/7/15 10:13
 * @brief viewBindingBaseAcy
 */
abstract class BaseAcy<VB : ViewBinding>():AppCompatActivity(), ActivityBinding<VB> by ActivityBindingDelegate() {

    protected val mContext:Context by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithBinding()
        init()
        initData(savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun initView()
    abstract fun onListener()
    protected open fun init() {}

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        return false
    }

    protected var touchHideSoftInput = true

    /**
     * 点击EditText文本框之外任何地方隐藏键盘
     * http://blog.csdn.net/mad1989/article/details/25069821
     *
     * @param ev
     * @return
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchHideSoftInput) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (isShouldHideInput(v, ev)) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v?.windowToken, 0)
                }
                return super.dispatchTouchEvent(ev)
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            return if (window.superDispatchTouchEvent(ev)) {
                true
            } else onTouchEvent(ev)
        } else {
            return super.dispatchTouchEvent(ev)
        }
    }



}