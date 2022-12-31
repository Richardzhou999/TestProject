package com.uwei.commom.network.rxjava3

import com.uwei.commom.utils.CHLog.e

import kotlin.Throws
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * @Author Charlie
 * @Date 2022/8/11 17:36
 * 重试机制
 */
class RetryWithDelay(): Function<Observable<out Throwable?>, Observable<Any>> {
    /**
     * 最大出错重试次数
     */
    private var maxRetries = 5
    /**
     * 重试间隔时间
     */
    private var retryDelayMillis = 1000
    /**
     * 当前出错重试次数
     */
    private var retryCount = 0

    constructor(maxRetries: Int, retryDelayMillis: Int) : this() {
        this.maxRetries = maxRetries
        this.retryDelayMillis = retryDelayMillis
    }

    @Throws(Exception::class)
    override fun apply(observable: Observable<out Throwable?>): Observable<Any> {
        return observable
            .flatMap(object : Function<Throwable?, ObservableSource<*>> {
                @Throws(Exception::class)
                override fun apply(throwable: Throwable?): ObservableSource<*>? {
                    if (++retryCount <= maxRetries) {
                        e("get error, it will try after " + retryDelayMillis * retryCount + " millisecond, retry count " + retryCount)
                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        return Observable.timer((retryDelayMillis * retryCount).toLong(),
                            TimeUnit.MILLISECONDS)
                    }
                    return Observable.error<Any>(throwable)
                }
            })
    }
}