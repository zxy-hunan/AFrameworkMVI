package com.xy.application

import android.os.Bundle
import com.dylanc.longan.toast
import com.xy.application.databinding.ActivityTestBinding
import com.xy.application.intent.TestIntent
import com.xy.application.vm.TestViewModel
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file TestAcy
 * @author zxy
 * @date 2024/7/15 17:54
 * @brief Test
 */
class TestAcy : MviAcy<ActivityTestBinding, TestViewModel, TestIntent>(vMCls = TestViewModel::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        viewModel.testReq()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is TestIntent.Test -> {
                    toast(it.data)
                }
            }
        }
    }

    override fun onListener() {
    }

}