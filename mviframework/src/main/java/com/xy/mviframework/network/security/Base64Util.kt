
import com.xy.mviframework.network.security.Base64
import java.io.IOException

object Base64Util {
    /**
     * 字节数组转Base64编码
     *
     * @param bytes 字节数组
     * @return 字节数组
     */
    fun byte2Base64(bytes: ByteArray?): String? {
        return Base64.encode(bytes)
    }


    /**
     * Base64编码转字节数组
     *
     * @param base64Key Base64编码
     * @return Base64编码
     * @throws IOException
     */
    @Throws(IOException::class)
    fun base642Byte(base64Key: String?): ByteArray? {
        return Base64.decode(base64Key)
    }
}