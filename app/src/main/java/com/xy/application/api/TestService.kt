package com.xy.application.api

import com.xy.application.data.BaseModel
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @file TestService
 * @author zxy
 * @date 2024/7/16 14:20
 * @brief apiservice
 */
interface TestService {
    @GET("system/article/list")
    fun articleList(@QueryMap params: Map<String,String>): Flow<BaseRes<List<BaseModel>>>
}