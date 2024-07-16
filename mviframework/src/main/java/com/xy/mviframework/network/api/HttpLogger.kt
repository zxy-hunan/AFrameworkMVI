package com.xy.mviframework.network.api

import com.xy.mviframework.network.tool.httpLog

/**
 * @file HttpLogger
 * @author zxy
 * @date 2024/7/16 9:04
 * @brief httplogger
 */
class HttpLogger : CostomHttpLoggingInterceptor.Logger {
    private val mMessage by lazy { StringBuilder() }
    override fun log(message: String) {
        var forMatMsg = message
        // 请求或者响应开始
        if (forMatMsg.startsWith("--> POST") || forMatMsg.startsWith("--> GET")) {
            mMessage.setLength(0)
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (forMatMsg.startsWith("{") && forMatMsg.endsWith("}") || forMatMsg.startsWith("[") && forMatMsg.endsWith(
                "]"
            )
        ) {
            forMatMsg = JsonUtil.formatJson(JsonUtil.decodeUnicode(forMatMsg))
        }
        mMessage.append(forMatMsg + "\n")
        // 响应结束，打印整条日志
        if (forMatMsg.startsWith("<-- END HTTP")) {
            httpLog("HttpLogger", mMessage.toString())
        }
    }

}