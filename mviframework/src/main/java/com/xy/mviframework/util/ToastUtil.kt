package com.xy.mviframework.util

import android.content.Context
import android.widget.Toast

/**
 * @file ToastUtil
 * @author Administrator
 * @date 2024/7/3 10:55
 * @brief toastools
 */
object ToastUtil {
    fun showShortToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}