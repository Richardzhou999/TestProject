package com.uwei.commom.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.subjects.BehaviorSubject


/**
 * @Author Charlie
 * @Date 2022/9/1 9:58
 */
class RxJavaLifecycle(subject: BehaviorSubject<Lifecycle.Event>) : LifecycleObserver {

    private val subject: BehaviorSubject<Lifecycle.Event>

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        subject.onNext(Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        subject.onNext(Lifecycle.Event.ON_START)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        subject.onNext(Lifecycle.Event.ON_RESUME)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        subject.onNext(Lifecycle.Event.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        subject.onNext(Lifecycle.Event.ON_DESTROY)
    }

    init {
        this.subject = subject
    }
}
