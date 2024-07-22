package com.xy.mviframework.network.security

import AESUtils
import RSAUtils
import SecurityConfiguration
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.security.MessageDigest

class DataReqResManager {

    var mapAes : MutableMap<String,String> = mutableMapOf()

    companion object {
        val instance: DataReqResManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataReqResManager()
        }
    }

    fun urlHandle(sb: StringBuffer,url:String ) : Int{
        var index  = 0
        if(url.contains(RequestType.DEFAULT.requestType)){
            sb.append(url.replace(RequestType.DEFAULT.requestType,""))
            index = 0
        }else if(url.contains(RequestType.SINGLE .requestType)){
            sb.append(url.replace(RequestType.SINGLE.requestType,""))
            index = 1
        }else if(url.contains(RequestType.TWO_WAY.requestType)){
            sb.append(url.replace(RequestType.TWO_WAY.requestType,""))
            index = 2
        }else{
            sb.append(url)
        }
        return index
    }

    fun reqHandleData(index :Int,pramJson:String,st: Long):String {
        var jPramJson =""
        when(index){
            0,1-> {
                jPramJson = pramJson
            }
            2 -> {
                var gson = Gson()
                var aesKey : String = AESUtils.randomSequence(32)
                val ivOffset = aesKey.substring(0, aesKey.length / 2)
                var reqMap :MutableMap<String,Any> = mutableMapOf()
                reqMap["aesKey"] = aesKey
                reqMap["ivOffset"] = ivOffset
                if(!pramJson.isNullOrEmpty()) {
                    reqMap["param"] = gson.fromJson(pramJson, HashMap<String, Any>().javaClass)
                }else{
                    reqMap["param"] = pramJson
                }
                var mapString = gson.toJson(reqMap)
                mapAes[st.toString()] = aesKey
                jPramJson = mapString
            }
            else -> {
                jPramJson = pramJson
            }
        }

        return  jPramJson
    }

    /**
     * 处理请求数据
     */
    fun reqHandleEncrypt(index :Int,pramJson:String):String {
        var jPramJson = pramJson
        when(index){
            0 -> {}
            1,2 -> {
                jPramJson = RSAUtils.encrypt(pramJson.toByteArray(Charsets.UTF_8),RSAUtils.getPublicKey(SecurityConfiguration.PK), true )
                    .toString()
            }
        }

        return  jPramJson
    }

    fun getHashByString(data: String): String? {
        return try {
            val messageDigest = MessageDigest.getInstance("SHA1")
            messageDigest.reset()
            messageDigest.update(data.toByteArray(charset("UTF-8")))
            var hash   = messageDigest.digest()
            val hexString = StringBuilder()
            for (b in hash) {
                val hex = Integer.toHexString(0xff and b.toInt())
                if (hex.length == 1) {
                    hexString.append('0')
                }
                hexString.append(hex)
            }
            return hexString.toString()
        } catch (e: Exception) {
            ""
        }
    }


    fun resHandleRemove(index :Int,st: Long) {
        when(index){
            0 , 1-> {}
            2 -> {
                mapAes.remove(st.toString())
            }
        }
    }

    fun getGson():Gson{
        var gsonBuilder = GsonBuilder()
        gsonBuilder.setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER)
        return gsonBuilder.create()
    }
}

