package com.xy.application.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @file BaseModel
 * @author zxy
 * @date 2024/7/19 11:41
 * @brief nullmodel
 */
@Serializable
data class BaseModel(
    @SerialName("id")
    val id:String="") {
}