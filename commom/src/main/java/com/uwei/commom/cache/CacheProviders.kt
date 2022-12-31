package com.uwei.commom.cache

import com.github.richard.gson.GsonSpeaker
import com.github.richard.runtime.rx_cache3.RxCache3
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

/**
 * @Author Charlie
 * @Date 2022/9/14 14:55
 */
object CacheProviders{

    @Synchronized
    fun <T> createCache(cacheDirectory: File, classProviders: Class<T>,
                        observable: Observable<T>,subscriber: Observer<T>?){
        RxCache3.Builder()
            .persistence(cacheDirectory, GsonSpeaker())
            .using(classProviders).also {
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            }
    }



}

