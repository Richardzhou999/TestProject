package com.uwei.commom.utils

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


/**
 * @Author Charlie
 * @Date 2022/7/19 14:35
 */
object InterceptorUtil {

    /**
     * 日志拦截器,用于打印返回请求的结果
     */
    fun logInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            try {
                Log.w("UWLog", "log:${URLDecoder.decode(message, "utf-8")}")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                Log.e("UWLog", "error:$message")
            }
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * 头部添加token
     */
    fun defaultHeader(tokenName: String?, signName: String?): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val token = SharedPrefUtils["token", ""]
            val sign = SharedPrefUtils["sign", ""]
            builder.header(tokenName ?: "token", token).header(signName?:"sign", sign)
            chain.proceed(builder.build())
        }
    }

    /**
     * 头部或者公共参数拦截器
     */
    fun paramInterceptor(
        headerMap: MutableMap<String, String>?,
        paramMap: MutableMap<String, Any>?
    ): Interceptor {
        return Interceptor { chain ->
            val url: HttpUrl = chain.request().url()
            val builder = chain.request().newBuilder()

            //头部添加
            headerMap?.let {
                for (key in it.keys) {
                    builder.header(key, it[key])
                }
            }

            //url拼接公共参数
            paramMap?.let {
                val urlBuilder = StringBuilder(url.toString())
                if (url.toString().contains("?")) urlBuilder.append("&") else urlBuilder.append("?")
                val paramBuilder = StringBuilder()
                for (key in it.keys) {
                    paramBuilder.append("&$key=").append(it[key])
                }
                builder.url(urlBuilder.append(paramBuilder).toString())
            }

            chain.proceed(builder.build())
        }
    }

}