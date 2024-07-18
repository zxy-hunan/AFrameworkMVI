package com.xy.mviframework.loadingstate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.xy.mviframework.R

/**
 * @file LoadingViewDelegate
 * @author zxy
 * @date 2024/7/18 9:31
 * @brief loading
 */
class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup) = inflater.inflate(R.layout.layout_loading, parent, false)
}