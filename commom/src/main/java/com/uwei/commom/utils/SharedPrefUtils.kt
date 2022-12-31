package com.uwei.commom.utils

import android.content.Context
import android.content.SharedPreferences
import com.uwei.commom.utils.SharedPrefUtils
import java.lang.Exception

/**
 * @Author Charlie
 * @Date 2022/7/26 17:28
 */
object SharedPrefUtils{

    private lateinit var sp: SharedPreferences

    fun put(key: String?, value: String?) {
        sp.edit().putString(key, value).commit()
    }

    operator fun get(key: String?, defValue: String?): String? {
        return sp.getString(key, defValue)
    }

    fun put(key: String?, value: Int?) {
        sp.edit().putInt(key, value!!).commit()
    }

    operator fun get(key: String?, defValue: Int?): Int? {
        return sp.getInt(key, defValue!!)
    }

    fun put(key: String?, value: Float?) {
        sp.edit().putFloat(key, value!!).commit()
    }

    operator fun get(key: String?, defValue: Float?): Float {
        return sp.getFloat(key, defValue!!)
    }

    fun put(key: String?, value: Long?) {
        sp.edit().putLong(key, value!!).commit()
    }

    operator fun get(key: String?, defValue: Long?): Long {
        return sp.getLong(key, defValue!!)
    }

    fun put(key: String?, value: Boolean) {
        sp.edit().putBoolean(key, value).commit()
    }

    operator fun get(key: String?, defValue: Boolean): Boolean {
        return sp.getBoolean(key, defValue)
    }

    fun put(key: String?, value: MutableSet<String>) {
        sp.edit().putStringSet(key,value).commit()
    }

    operator fun get(key: String?, defValue: MutableSet<String>?): MutableSet<String>? {
        return sp.getStringSet(key,defValue)
    }

    fun remove(key: String?) {
        try {
            sp.edit().remove(key).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clear() {
        try {
            sp.edit().clear().commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getInstance(context: Context,name: String): SharedPreferences{
        sp = context.getSharedPreferences(
                name, 0)
        return sp
    }

}