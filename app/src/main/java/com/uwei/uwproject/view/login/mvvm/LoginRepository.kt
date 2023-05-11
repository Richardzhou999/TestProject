package com.uwei.uwproject.view.login.mvvm

import com.uwei.base.mvp.BaseModel
import com.uwei.manager.BasicResponse
import com.uwei.commom.rx.RxHelper
import com.uwei.uwproject.base.CardApplication
import com.uwei.uwproject.bean.UserBean
import com.uwei.uwproject.constant.Constant
import com.uwei.uwproject.network.ApiService
import kotlinx.coroutines.flow.Flow

class LoginRepository : BaseModel() {

    fun getLoginData(phone: String): Flow<BasicResponse<UserBean>> {
        val service = RxHelper.getApiServerFlow(CardApplication.getContext(), Constant.BASE_URL, "Authorization", ApiService::class.java)
        return service.login2(phone)
    }

}