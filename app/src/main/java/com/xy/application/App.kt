package com.xy.application

import com.xy.mviframework.base.BaseApp

/**
 * @file App
 * @author zxy
 * @date 2024/7/22 9:48
 * @brief application
 */
class App :BaseApp() {

    override fun onCreate() {
        super.onCreate()
        BASEURL="http://gyuelife.online/prod-api/"
    }
}