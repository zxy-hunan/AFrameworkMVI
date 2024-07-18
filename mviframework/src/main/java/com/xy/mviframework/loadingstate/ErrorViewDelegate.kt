package com.xy.mviframework.loadingstate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.xy.mviframework.R

/**
 * @file ErrorViewDelegate
 * @author zxy
 * @date 2024/7/18 9:33
 * @brief error
 */
class ErrorViewDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup)= inflater.inflate(R.layout.layout_error, parent, false)
}