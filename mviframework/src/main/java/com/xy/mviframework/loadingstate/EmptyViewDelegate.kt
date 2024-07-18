package com.xy.mviframework.loadingstate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.xy.mviframework.R

/**
 * @file EmptyViewDelegate
 * @author zxy
 * @date 2024/7/18 9:34
 * @brief empty
 */
class EmptyViewDelegate : LoadingStateView.ViewDelegate(ViewType.EMPTY) {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup) = inflater.inflate(R.layout.layout_empty, parent, false)
}