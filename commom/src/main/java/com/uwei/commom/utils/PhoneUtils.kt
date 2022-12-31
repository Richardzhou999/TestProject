package com.uwei.commom.utils

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * @Author Charlie
 * @Date 2022/7/27 17:16
 */
object PhoneUtils {

    @JvmStatic
    fun isMobilPhone(phone: String): Boolean {
        if (TextUtils.isEmpty(phone)) {
            return false
        }
        val regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16([0-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
        return if (phone.length != 11) {
            false
        } else {
            val p = Pattern.compile(regex)
            val m = p.matcher(phone)
            m.matches()
        }
    }
}