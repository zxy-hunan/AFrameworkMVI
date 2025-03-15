package com.xy.mviframework.network.def

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @file BaseResponse
 * @author zxy
 * @date 2024/7/16 11:30
 * @brief Common Response Data
 */
@Serializable
data class BaseRes<T>(
    @SerialName("code")
    val code: String = "",
    @SerialName("msg")
    val msg: String = "",
    @SerialName("total")
    val total: Int = 0,
    @SerialName("data")
    val data: T?,
    @SerialName("rows")
    val rows: T?,
    @SerialName("token")
    val token: T?,
    @SerialName("user")
    val user: T?


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