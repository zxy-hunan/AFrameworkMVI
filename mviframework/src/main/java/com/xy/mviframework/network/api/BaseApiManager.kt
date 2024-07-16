package com.xy.mviframework.network.api

import com.xy.mviframework.base.BaseApp
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @file BaseApiManager
 * @author zxy
 * @date 2024/7/16 9:03
 * @brief BaseApi
 */
open class BaseApiManager {
    companion object {
        private const val CONNECT_TIMEOUT = 10
        private const val WRITE_TIMEOUT = 10
        private const val READ_TIMEOUT = 10
        private const val CALL_TIMEOUT = 15
        val httpLogger by lazy { HttpLogger() }


        @JvmStatic
        fun getHttpClient(callTimeOut: Long = CALL_TIMEOUT.toLong()): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            builder.callTimeout(callTimeOut, TimeUnit.SECONDS)
            return builder
        }
        @JvmStatic
        fun getCache(): Cache {
            return Cache(
                //100MB
                File(BaseApp.instance.applicationContext.cacheDir, "OkhttpCache"),
                1024 * 1024 * 100
            )
        }

    }
}