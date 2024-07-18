package com.xy.application.intent

import com.xy.mviframework.base.vm.BaseIntent

/**
 * @file TestIntent
 * @author zxy
 * @date 2024/7/15 18:00
 * @brief testintent
 */
open class TestIntent : BaseIntent() {

    data class Test(val data:String):TestIntent()
}