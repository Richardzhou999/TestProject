package com.uwei.commom.rx

import android.accounts.NetworkErrorException
import com.uwei.commom.network.BasicResponse
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @Author Charlie
 * @Date 2022/8/13 9:54
 */
abstract class RxBaseCallBack<T> : Subscriber<BasicResponse<T>> {


    override fun onSubscribe(s: Subscription?) {
        onStart()
    }

    override fun onNext(response: BasicResponse<T>) {
        if(response.success){
            response.data?.let {
                onSuccess(it)
            }
            response.result?.let {
                onSuccess(it)
            }
        }else{
            onError(response)
        }
    }

    override fun onError(throwable: Throwable) {
        if (throwable is ConnectException ||
                throwable is TimeoutException ||
                throwable is NetworkErrorException ||
                throwable is UnknownHostException) {
            try {
                onFailure(throwable, false)
            } catch (e1: java.lang.Exception) {
                e1.printStackTrace()
            }
        } else {
            try {
                onFailure(throwable, true)
            } catch (e1: java.lang.Exception) {
                e1.printStackTrace()
            }
        }

    }

    override fun onComplete() {
        onFinish()
    }

    /**
     * 开始请求
     */
    abstract fun onStart()

    /**
     * 请求成功且返回码为200
     * @param response
     * @return
     */
    abstract fun onSuccess(response: T)

    /**
     * 请求错误
     * @param response
     */
    abstract fun onError(response: BasicResponse<T>)

    /**
     * 请求失败
     * @param e
     * @param netWork
     */
    @Throws(Exception::class)
    abstract fun onFailure(e: Throwable?, netWork: Boolean)

    /**
     * 请求结束
     */
    abstract fun onFinish()

}