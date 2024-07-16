package com.xy.mviframework.network.default

import kotlinx.serialization.Serializable

/**
 * @file BaseResponse
 * @author zxy
 * @date 2024/7/16 11:30
 * @brief Common Response Data
 */
@Serializable
data class BaseResponse<T>(
    val code: String = "",
    val message: String = "",
    val data: T?
) {

 /*   val data: T
        get() {
            originData?.let {
                return it
            }
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson("{}", type)
        }*/

}