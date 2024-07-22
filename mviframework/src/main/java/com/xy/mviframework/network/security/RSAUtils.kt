
import Base64Util
import java.io.ByteArrayOutputStream
import java.security.Key
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object RSAUtils {
    private val RSA_MODE = "RSA/ECB/PKCS1Padding"
    private val MAX_ENCRYPT_BLOCK = 117
    private val MAX_DECRYPT_BLOCK = 128

    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     *
     * @return PublicKey公钥对象
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPublicKey(base64StrKey: String?): PublicKey? {
        val keyBytes: ByteArray? = Base64Util.base642Byte(base64StrKey)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     *
     * @return PrivateKey私钥对象
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPrivateKey(base64StrKey: String?): PrivateKey? {
        val keyBytes: ByteArray? = Base64Util.base642Byte(base64StrKey)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }


    /**
     * 加密
     * @return 加密后的Base64
     * @throws Exception
     */
    fun encrypt(content: ByteArray, key: Key?, isSubsection: Boolean): String? {
        val cipher = Cipher.getInstance(RSA_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        if (isSubsection) {
            val inputLen = content.size
            val out = ByteArrayOutputStream()
            var offSet = 0
            var cache: ByteArray
            var i = 0
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                cache = if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cipher.doFinal(content, offSet, MAX_ENCRYPT_BLOCK)
                } else {
                    cipher.doFinal(content, offSet, inputLen - offSet)
                }
                out.write(cache, 0, cache.size)
                i++
                offSet = i * MAX_ENCRYPT_BLOCK
            }
            val encryptedData = out.toByteArray()
            out.close()
            return Base64Util.byte2Base64(encryptedData)
        }
        val result = cipher.doFinal(content)
        return Base64Util.byte2Base64(result)
    }


    /**
     * 解密
     * @return 解密后的字节数组
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decrypt(content: ByteArray, key: Key?, isSubsection: Boolean): ByteArray? {
        val cipher = Cipher.getInstance(RSA_MODE)
        cipher.init(Cipher.DECRYPT_MODE, key)
        if (isSubsection) {
            val inputLen = content.size
            val out = ByteArrayOutputStream()
            var offSet = 0
            var cache: ByteArray
            var i = 0
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                cache = if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK)
                } else {
                    cipher.doFinal(content, offSet, inputLen - offSet)
                }
                out.write(cache, 0, cache.size)
                i++
                offSet = i * MAX_DECRYPT_BLOCK
            }
            val decryptedData = out.toByteArray()
            out.close()
            return decryptedData
        }
        return cipher.doFinal(content)
    }
}