package com.uwei.commom.utils

import java.lang.StringBuilder
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * @Author Charlie
 * 加密文件
 */

fun String.sha1(): String {
    val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
    return byte2hex(bytes)
}

fun String.md5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return byte2hex(bytes)
}

fun byte2hex(bytes: ByteArray): String {
    var stringBuilder = StringBuilder()
    var stmp = ""
        bytes.forEach { value ->
        val hex = value.toInt() and (0xFF)
        stmp = Integer.toHexString(hex)
        if (stmp.length == 1) {
            stringBuilder.append(0).append(stmp)
        } else {
            stringBuilder.append(stmp)
        }
    }
    return stringBuilder.toString()
}

