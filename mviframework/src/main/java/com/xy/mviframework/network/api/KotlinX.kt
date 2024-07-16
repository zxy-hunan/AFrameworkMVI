package com.xy.mviframework.network.api

import kotlinx.serialization.json.Json

object KotlinX {
    val jsonDecoder = Json {
        ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配
        coerceInputValues = true // 如果JSON字段是Null则使用默认值
        allowStructuredMapKeys = true //启用Map序列化. 默认情况是不能序列化Map
    }
}