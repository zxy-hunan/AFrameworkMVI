package com.xy.mviframework.util

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.dylanc.longan.statusBarHeight

val FragmentActivity.windowInsetsCompat: WindowInsetsCompat?
    get() = ViewCompat.getRootWindowInsets(findViewById(android.R.id.content))

fun FragmentActivity.getNavigationBarsHeight(): Int {
    val windowInsetsCompat = windowInsetsCompat ?: return 0
    return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
}

fun View.setMarginStatusBar() {


    val lp = this.layoutParams as ViewGroup.MarginLayoutParams
    lp.setMargins(0, statusBarHeight, 0, 0)
    this.layoutParams = lp
}