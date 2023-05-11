package com.uwei.commom.network

import android.content.Context
import com.uwei.commom.rx.JXConverterFactory
import com.uwei.commom.utils.InterceptorUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Author Charlie
 * @Date 2022/8/1 15:22
 */
object RetrofitManager {

    /**
     * 超时时间
     */
    const val TIME_OUT = 15
    /**
     * 缓存内存 - 10 MB
     */
    const val DEFAULT_DIR_CACHE = 10 * 1024 * 1024;

    /**
     * 默认Okhttp,绑定日志
     * @param headerMap
     * @param paramMap 若有参数则加上
     */
    private fun getOkHttpClient(context: Context,tokenName: String?,signName: String?,
                                headerMap: MutableMap<String, String>?,
                                paramMap: MutableMap<String, Any>?,isCache: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(InterceptorUtil.logInterceptor())
            .addInterceptor(InterceptorUtil.defaultHeader(tokenName,signName))
            .addInterceptor(InterceptorUtil.paramInterceptor(headerMap,paramMap))
        if(isCache){
            var cacheFile = File(context.cacheDir, "cacheData")
            var cache = Cache(cacheFile, DEFAULT_DIR_CACHE.toLong())
            builder.addInterceptor(CacheInterceptor(context))
            builder.addNetworkInterceptor(CacheInterceptor(context))
                .cache(cache)
        }
        return builder.build()
    }

    fun getRetrofit2(context: Context, url: String,tokenName: String,isCache: Boolean = false): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(JXConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .client(getOkHttpClient(context,tokenName,null,null,isCache))
                .build()
    }

    fun getRetrofit3(context: Context, url: String, tokenName: String, isCache: Boolean = false): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(JXConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(url)
            .client(getOkHttpClient(context,tokenName,null,null,isCache))
            .build()
    }

    fun getRetrofitFlow(context: Context, url: String, tokenName: String, isCache: Boolean = false): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(JXConverterFactory.create())
                .addCallAdapterFactory(FlowAdapterFactory.create(true))
                .baseUrl(url)
                .client(getOkHttpClient(context,tokenName,null,null,isCache))
                .build()
    }

    fun getRetrofit3(context: Context, url: String, headerMap: MutableMap<String,String>?,
                     paramMap: MutableMap<String,Any>?, isCache: Boolean = false): Retrofit{
        return Retrofit.Builder()
                .addConverterFactory(JXConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(url)
                .client(getOkHttpClient(context,"",headerMap,paramMap,isCache))
                .build()
    }

}