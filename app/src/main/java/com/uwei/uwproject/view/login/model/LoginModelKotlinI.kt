package com.uwei.uwproject.view.login.model

import androidx.lifecycle.LifecycleOwner
import com.uwei.commom.rx.RxHelper.createCache
import com.uwei.base.mvp.IBaseModel
import com.uwei.uwproject.view.login.LoginContract
import com.uwei.commom.rx.DefaultDialogCallBack
import com.uwei.uwproject.base.CardApplication
import okhttp3.RequestBody
import com.uwei.commom.rx.DefaultBackCallBack
import com.uwei.uwproject.view.login.LoginCacheProviders
import com.google.gson.Gson
import com.uwei.commom.utils.sha1
import java.util.HashMap

/**
 * @Author Charlie
 * @Date 2022/7/27 17:47
 */
class LoginModelKotlinI : IBaseModel, LoginContract.LoginModel {



    override fun login(
        phone: String,
        number: String,
        callBack: DefaultDialogCallBack<*>,
        owner: LifecycleOwner
    ) {

        val map = HashMap<String, Any>()
        map["account"] = phone
        map["password"] = number.sha1()
        val gson = Gson()
        val json = gson.toJson(map)
        val loginBody = RequestBody.create(null, json)
        //createSubscribe(service?.V2Login(loginBody), callBack, owner)
    }

    override fun getVerificationCode(
        phone: String,
        callBack: DefaultBackCallBack<*>?,
        owner: LifecycleOwner
    ) {

        val providers = createCache(
            CardApplication.getContext().externalCacheDir!!,
            LoginCacheProviders::class.java
        )
//        setCache(
//            providers.getLogin(
//                service!!.getVerificationCode(phone),
//                DynamicKey(phone), EvictDynamicKey(false)
//            ), callBack, owner
//        )
    }
}