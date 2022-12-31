package com.uwei.commom.utils

import java.security.MessageDigest

/**
 * @Author Charlie
 * @Date 2022/8/11 10:24
 */
object MD5Utils {

    fun String.md5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.hex()
    }

    private fun ByteArray.hex(): String {
        return joinToString("") { "%02X".format(it) }
    }

}