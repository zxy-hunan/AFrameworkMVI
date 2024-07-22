package com.xy.mviframework.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResult<T>(
     @SerialName("code")
     val code: String = "",
     @SerialName("msg")
     val msg: String = "",
     @SerialName("data")
     val `data`: T?
) {

}