package com.uwei.commom.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.uwei.commom.utils.CHLog

/**
 * @Author Charlie
 * @Date 2022/8/17 10:56
 * 监听App在前后台状态
 */
class ProcessLifecycle : LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForeground(){
        CHLog.e("前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        CHLog.e("后台")
    }

}