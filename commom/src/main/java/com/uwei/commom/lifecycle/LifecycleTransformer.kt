package com.uwei.commom.lifecycle

import androidx.lifecycle.Lifecycle
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

/**
 * @Author Charlie
 * @Date 2022/9/1 10:03
 */
class LifecycleTransformer<T>(lifecycle: Observable<Lifecycle.Event>) :
    ObservableTransformer<T, T> {
    private val mLifecycle: Observable<Lifecycle.Event>

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.unsubscribeOn(Schedulers.newThread())
    }

    init {
        mLifecycle = lifecycle
    }


}



