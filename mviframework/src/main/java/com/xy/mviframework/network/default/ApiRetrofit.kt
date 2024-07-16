package com.xy.mviframework.network.default

import com.coder.vincent.sharp_retrofit.call_adapter.flow.FlowCallAdapterFactory
import com.coder.vincent.sharp_retrofit.interceptors.TimeoutInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.xy.mviframework.base.BaseApp
import com.xy.mviframework.network.api.BaseApiManager
import com.xy.mviframework.network.api.KotlinX
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * @file ApiRetrofit
 * @author zxy
 * @date 2024/7/16 10:42
 * @brief default apimanager
 */
open class ApiRetrofit : BaseApiManager() {
    companion object {
        val instance: ApiRetrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiRetrofit()
        }
    }

    private val httpClient by lazy {
        val builder = getHttpClient()
        //可添加多個
        if (BaseApp.instance.getInterceptors().isNotEmpty()) {
            for (interceptor in BaseApp.instance.getInterceptors()) {
                builder.addInterceptor(interceptor)
            }
        }
        builder.addInterceptor(TimeoutInterceptor())
        builder.cache(getCache())
        builder
    }

    val retrofit: Retrofit by lazy {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(BaseApp.instance.BASEURL)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(KotlinX.jsonDecoder.asConverterFactory(contentType))
            .build()
        retrofit
    }
}

val apiRetrofit: Retrofit
    get() = ApiRetrofit.instance.retrofit