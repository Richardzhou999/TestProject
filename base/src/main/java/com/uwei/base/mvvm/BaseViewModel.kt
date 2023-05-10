package com.uwei.base.mvvm

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uwei.manager.CoroutineException
import com.uwei.manager.IViewModel
import com.uwei.manager.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), IViewModel, DefaultLifecycleObserver {

    val defUI: UIChange by lazy { UIChange() }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launch(block: suspend CoroutineScope.() -> Unit) =
            viewModelScope.launch(CoroutineException.netException){ block() }



    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
    }

    override fun showLoading(text: String) {
        defUI.showDialog.postValue(text)
    }

    override fun dismissLoading() {
        defUI.dismissDialog.call()
    }

}