package com.xy.mviframework.network.api

import com.blankj.utilcode.util.EncodeUtils
import com.xy.mviframework.network.security.DataReqResManager
import com.xy.mviframework.network.tool.StringTools
import com.xy.mviframework.network.tool.logI
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone
import java.util.UUID


class DataInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val sb = StringBuffer()

        var ts = getLocalDataTimeString(TimeZone.getDefault().id)
        var randCode = generateRandomString()
        var index = DataReqResManager.instance.urlHandle(sb,URLDecoder.decode(request.url.toString(),"UTF-8"))
        val newRequestBuilder = request.newBuilder()
                .headers(request.headers)
        var resMethod = request.method
        when(resMethod.uppercase()){
            "POST","PUT","DELETE" -> {
                val requestBody = request.body?:"".toRequestBody()
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val charset = Charset.forName("UTF-8")
                var requestBodyStr = buffer.readString(charset)

                var reqHandleData = DataReqResManager.instance.reqHandleData(index,requestBodyStr,ts)
                var dataContext = DataReqResManager.instance.reqHandleEncrypt(index,reqHandleData)
                newRequestBuilder.method(request.method,buildJsonPostRequest(requestBody.contentType()?:"application/json".toMediaTypeOrNull(), dataContext))
            }
            "GET"  -> {
                newRequestBuilder.method(request.method,null)
            }
        }
        if (sb.toString().indexOf("?") >= 0) {
            sb.append("&ts=${ts}")
        } else {
            sb.append("?ts=${ts}")
        }
        sb.append("&rand_code=${randCode}")

        val newRequest = newRequestBuilder
            .url(sb.toString())
            .build()
        logI("----------Request Start----------------\n",
            String.format("\ntime:%s", ts),
            String.format("\n接口:%s", newRequest.url),
            String.format("\nMothod:%s", newRequest.method),
            String.format("\nheaders:%s", newRequest.headers.toString()),
            String.format("\n参数:%s", newRequest.body?.string() ?: ""))

        val response = chain.proceed(newRequest)

        var gson = StringTools.getGson()
        var resBody : ResponseBody? = response.body
        val content =  resBody?.string()
        val contentType = resBody?.contentType()

        logI("----------Request end------${ts}----------\n",
            String.format("\ncode:%s", response.code),
            String.format("\nmessage:%s", response.message),
            String.format("\n返回数据:%s", content))


        return response.newBuilder()
            .body(ResponseBody.Companion.create(contentType,content!!.encodeToByteArray()))
            .build()

    }

    fun responseCodeHandle(code:String,index:Int,ts :Long){
        if (code == "401" || code == "4001") {
            //重新登陆

        }else if (code == "4004") {
            //强制设置数据

        }
    }


    fun buildJsonPostRequest(contentType: MediaType?, json: String):RequestBody{
        val requestBody: RequestBody = RequestBody.create(contentType,json)
        return requestBody
    }

    fun getLocalDataTimeString(local: String?): Long {
        val cal: Calendar = GregorianCalendar(TimeZone.getTimeZone(local))
//        cal.timeInMillis = Calendar.getInstance().timeInMillis
        return cal.time.time/1000
    }

    fun generateRandomString(): String {
        return UUID.randomUUID().toString().replace("-","").substring(0, 16)
    }
}

fun RequestBody?.string(): String {
    if (this != null && this.contentLength() > 0) {
        val buffer = Buffer()
        this.writeTo(buffer)
        val stringBody = EncodeUtils.urlDecode(buffer.readUtf8())
        return stringBody
    }
    return ""
}