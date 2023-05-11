package com.uwei.uwproject.view.login.model

import android.annotation.SuppressLint
import com.uwei.base.mvvm.BaseViewModel
import com.uwei.base.mvvm.asSuccess
import com.uwei.base.mvvm.bindLoading
import com.uwei.uwproject.bean.UserBean
import com.uwei.uwproject.view.login.mvvm.LoginRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onCompletion

class LoginMVModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }

    private val _liveData = MutableSharedFlow<UserBean>()
    val mBanners: SharedFlow<UserBean> = _liveData


    @OptIn(InternalCoroutinesApi::class)
    fun login(phone: String?) {
        launch {
            if (phone != null) {
                    loginRepository.getLoginData(phone)
                        .asSuccess()
                        .onCompletion {  }
                        .bindLoading(this@LoginMVModel)
                        .collect(_liveData)
            }
        }

    }
}