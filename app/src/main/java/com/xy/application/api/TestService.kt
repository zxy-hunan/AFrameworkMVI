package com.xy.application.api

import com.xy.mviframework.network.default.BaseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @file TestService
 * @author zxy
 * @date 2024/7/16 14:20
 * @brief apiservice
 */
interface TestService {
    @POST("{test}")
    fun test(@Path(value = "test", encoded = true) test:String): Flow<BaseResponse<String>>
}