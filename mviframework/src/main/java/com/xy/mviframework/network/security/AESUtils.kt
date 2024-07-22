
import android.util.Base64
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.GeneralSecurityException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Random
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {
    private val AES_MODE = "AES/CBC/PKCS7Padding"
    private val CHARSET = "UTF-8"
    private val CIPHER = "AES"
    private val HASH_ALGORITHM = "SHA-256"

    /**
     * Generates SHA256 hash of the password which is used as key
     *
     * @param password used to generated key
     * @return SHA256 of the password
     */
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun generateKey(password: String): SecretKeySpec? {
        val digest = MessageDigest.getInstance(HASH_ALGORITHM)
        val bytes = password.toByteArray(charset(CHARSET))
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        return SecretKeySpec(key, CIPHER)
    }

    /**
     * Encrypt and encode message using 256-bit AES with key generated from password.
     *
     * @param password used to generated key
     * @param message  the thing you want to encrypt assumed String UTF-8
     * @return Base64 encoded CipherText
     * @throws GeneralSecurityException if problems occur during encryption
     */
    @Throws(GeneralSecurityException::class)
    fun encrypt(password: String, message: String): String? {
        return try {
            val iv_bytes =
                password.substring(0, password.length / 2).toByteArray(StandardCharsets.UTF_8)
            val skForAES = SecretKeySpec(password.toByteArray(StandardCharsets.UTF_8), "AES")
            val cipherText = encrypt(skForAES, iv_bytes, message.toByteArray(charset(CHARSET)))
            //NO_WRAP is important as was getting \n at the end
            Base64.encodeToString(cipherText, Base64.NO_WRAP)
        } catch (e: UnsupportedEncodingException) {
            throw GeneralSecurityException(e)
        } catch (e: NullPointerException) {
            throw NullPointerException()
        }
    }


    /**
     * More flexible AES encrypt that doesn't encode
     *
     * @param key     AES key typically 128, 192 or 256 bit
     * @param iv      Initiation Vector
     * @param message in bytes (assumed it's already been decoded)
     * @return Encrypted cipher text (not encoded)
     * @throws GeneralSecurityException if something goes wrong during encryption
     */
    @Throws(GeneralSecurityException::class)
    fun encrypt(key: SecretKeySpec?, iv: ByteArray?, message: ByteArray?): ByteArray {
        val cipher = Cipher.getInstance(AES_MODE)
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
        return cipher.doFinal(message)
    }


    /**
     * Decrypt and decode ciphertext using 256-bit AES with key generated from password
     *
     * @param password                used to generated key
     * @param base64EncodedCipherText the encrpyted message encoded with base64
     * @return message in Plain text (String UTF-8)
     * @throws GeneralSecurityException if there's an issue decrypting
     */
    @Throws(GeneralSecurityException::class)
    fun decrypt(password: String, base64EncodedCipherText: String?): String {
        return try {
            val iv_bytes =
                password.substring(0, password.length / 2).toByteArray(StandardCharsets.UTF_8)
            val skForAES = SecretKeySpec(password.toByteArray(StandardCharsets.UTF_8), "AES")
            val decodedCipherText = Base64.decode(base64EncodedCipherText, Base64.NO_WRAP)
            val decryptedBytes = decrypt(skForAES, iv_bytes, decodedCipherText)
            String(decryptedBytes, charset(CHARSET))
        } catch (e: UnsupportedEncodingException) {
            throw GeneralSecurityException(e)
        } catch (e: NullPointerException) {
            throw NullPointerException()
        }
    }

    /**
     * More flexible AES decrypt that doesn't encode
     *
     * @param key               AES key typically 128, 192 or 256 bit
     * @param iv                Initiation Vector
     * @param decodedCipherText in bytes (assumed it's already been decoded)
     * @return Decrypted message cipher text (not encoded)
     * @throws GeneralSecurityException if something goes wrong during encryption
     */
    @Throws(GeneralSecurityException::class)
    fun decrypt(key: SecretKeySpec?, iv: ByteArray?, decodedCipherText: ByteArray?): ByteArray {
        val cipher = Cipher.getInstance(AES_MODE)
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        return cipher.doFinal(decodedCipherText)
    }


    /*************************随机生成key */
    private val random = Random()
    private val ALPHA = 'a'
    private val NUMBER = '0'
    private val UPPERCASEALPHA = 'A'
    private val ALPHABOUND = 26
    private val NUMBERBOUND = 10
    private val RANDOMTYPEBOUND = 2

    fun randomSequence(size: Int): String {
        val sequence = StringBuilder()
        for (i in 0 until size) {
            sequence.append(getRandomValue())
        }
        return sequence.toString()
    }

    private fun getRandomValue(): Char {
        val index = random.nextInt(RANDOMTYPEBOUND)
        return if (index == 0) {
            randomAlpha()
        } else {
            randomNumber()
        }
//        else{
//            return randomUpperAlpha();
//        }
    }

    private fun randomAlpha(): Char {
        return randomValue(ALPHA.code, ALPHABOUND)
    }

    private fun randomNumber(): Char {
        return randomValue(NUMBER.code, NUMBERBOUND)
    }

    private fun randomUpperAlpha(): Char {
        return randomValue(UPPERCASEALPHA.code, ALPHABOUND)
    }

    private fun randomValue(start: Int, bound: Int): Char {
        val index = random.nextInt(bound)
        return (start + index).toChar()
    }
}