package com.uwei.commom.rx

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.github.richard.gson.GsonSpeaker
import com.github.richard.runtime.rx_cache3.RxCache3
import com.rxjava.rxlife.lifeOnMain
import com.uwei.commom.network.RetrofitManager.getRetrofit2
import com.uwei.commom.network.RetrofitManager.getRetrofit3
import com.uwei.commom.network.RetrofitManager.getRetrofitFlow
import com.uwei.commom.network.rxjava3.RetryWithDelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File


/**
 * @Author Charlie
 * @Date 2022/8/13 15:25
 */
object RxHelper {

    fun <T> createSubscribe(observable: Observable<T>?,
                            subscriber: Observer<T>?,
                            owner: LifecycleOwner) {
        observable?.let { observable->
            subscriber?.let { subscriber->
                observable.subscribeOn(Schedulers.newThread())
                    .retryWhen(RetryWithDelay())
                    .lifeOnMain(owner)
                    .subscribe(subscriber)
            }
        }
    }

    fun <T> createSubscribeJava2(observable: io.reactivex.Observable<T>?,
                                 subscriber: io.reactivex.Observer<T>?,
                                 owner: LifecycleOwner) {
        observable?.let { observable ->
            subscriber?.let {
                observable.subscribeOn(io.reactivex.schedulers.Schedulers.newThread())
                    .retryWhen(com.uwei.commom.network.rxjava2.RetryWithDelay())
                    .subscribe(it)
            }
        }
    }

    /**
     * 加入缓存
     */

    fun <R> createCache(cacheDirectory: File, classProviders: Class<R>): R{
        return RxCache3.Builder()
            .persistence(cacheDirectory, GsonSpeaker())
            .using(classProviders)
    }

    fun <T> setCache(observable: Observable<T>?, subscriber: Observer<T>?,
                     owner: LifecycleOwner){
        observable?.let { observable ->
            subscriber?.let { subscriber->
                observable.subscribeOn(Schedulers.newThread())
                    .retryWhen(RetryWithDelay())
                    .observeOn(AndroidSchedulers.mainThread())
                    .lifeOnMain(owner)
                    .subscribe(subscriber)
            }
        }
    }



//    fun <T> applyFlowableSchedulers(flowable: Flowable<T>, subscriber: FlowableSubscriber<T>?, owner: LifecycleOwner) {
//        subscriber?.let {
//            flowable.subscribeOn(Schedulers.io())
//                    .lifeOnMain(owner)
//                    .subscribe(it)
//        }
    //   }

    fun <T> getApiServer(context: Context, url: String, param: String, clazz: Class<T>,isCache: Boolean = false): T =
         getRetrofit2(context, url, param, null,null,
             null,isCache).create(clazz)
             ?: throw RuntimeException("Api service is null!")

    fun <T> getApiServer(context: Context, url: String, clazz: Class<T>, isCache: Boolean = false): T =
        getRetrofit3(context, url,null,null,
            null,null,isCache).create(clazz)?:
            throw RuntimeException("Api service is null!")


    fun <T> getApiServerFlow(context: Context, url: String, clazz: Class<T>,isCache: Boolean = false): T =
       getRetrofitFlow(context, url, null, null,null,null,isCache).create(clazz)
           ?: throw RuntimeException("Api service is null!")



}







