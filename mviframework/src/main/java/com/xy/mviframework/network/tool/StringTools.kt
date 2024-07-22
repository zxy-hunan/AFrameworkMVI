package com.xy.mviframework.network.tool

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.security.MessageDigest

object StringTools {

    /** md5加密 */
    fun md5(content: String): String {
        val hash = MessageDigest.getInstance("MD5").digest(content.toByteArray())
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            var str = Integer.toHexString(b.toInt())
            if (b < 0x10) {
                str = "0$str"
            }
            hex.append(str.substring(str.length -2))
        }
        return hex.toString()
    }

    fun getGson(): Gson {
        var gsonBuilder = GsonBuilder()
        gsonBuilder.setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER)
        return gsonBuilder.create()
    }

    fun getMiddleIndex(list: List<*>): Int {
        if(list.isNullOrEmpty()){ return 0}
        val size = list.size
        return if (size % 2 == 0) {
            // 如果列表长度为偶数，则中间位置有两个索引
            size / 2 - 1
        } else {
            // 如果列表长度为奇数，则直接返回中间位置的索引
            size / 2
        }
    }
}