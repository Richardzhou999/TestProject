package com.uwei.commom.network

import android.content.Context
import com.uwei.commom.utils.NetworkUtils
import com.uwei.commom.utils.CHLog
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Author Charlie
 * @Date 2022/8/24 11:19
 * 缓存拦截
 */
class CacheInterceptor(var context: Context,
                       private val TIMEOUT_CONNECT: Int = 5,
                       private val TIMEOUT_DISCONNECT: Int = 30) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if(!NetworkUtils.isConnected(context.applicationContext)){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//仅仅使用缓存
                    .build();
            CHLog.d("CacheInterceptor no network")
        }
        val originalResponse = chain.proceed(request);

        return if(NetworkUtils.isConnected(context.applicationContext)){
            originalResponse.newBuilder()
                    //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                    .header("Cache-Control", "public, max-age=$TIMEOUT_CONNECT")
                    .removeHeader("Pragma")
                    .build()
        }
        else{
            originalResponse.newBuilder()
                    //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                    .header("Cache-Control", "public, only-if-cached, max-stale=$TIMEOUT_DISCONNECT")
                    .removeHeader("Pragma")
                    .build()
        }
    }
}