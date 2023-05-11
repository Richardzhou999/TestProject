package com.uwei.uwproject.view.login.model;

import androidx.lifecycle.LifecycleOwner;

import com.google.gson.Gson;
import com.uwei.base.mvp.IBaseModel;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.rx.DefaultDialogCallBack;
import com.uwei.commom.rx.RxHelper;
import com.uwei.commom.utils.MessageDigestUtilsKt;
import com.uwei.uwproject.base.CardApplication;
import com.uwei.uwproject.constant.Constant;
import com.uwei.uwproject.network.ApiService;
import com.uwei.uwproject.view.login.LoginCacheProviders;
import com.uwei.uwproject.view.login.LoginContract;

import java.util.HashMap;

import okhttp3.RequestBody;


/**
 * @Author Charlie
 * @Date 2022/7/27 17:47
 */
public class LoginModel implements LoginContract.LoginModel, IBaseModel {

    @Override
    public void login(String phone, String number, DefaultDialogCallBack callBack, LifecycleOwner owner) {
        ApiService service = RxHelper.INSTANCE.getApiServer(CardApplication.getContext(), Constant.BASE_URL
                , ApiService.class,false);
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",phone);
        map.put("project","IOT");
        map.put("password", MessageDigestUtilsKt.sha1(number));

        Gson gson = new Gson();
        String json = gson.toJson(map);
        RequestBody loginBody = RequestBody.create(null,json);
        RxHelper.INSTANCE.createSubscribe(service.V2Login(loginBody),callBack,owner);
    }

    @Override
    public void getVerificationCode(String phone, DefaultBackCallBack callBack, LifecycleOwner owner) {

//        ApiService service = RxHelper.INSTANCE.getApiServer(CardApplication.getContext(), Constant.BASE_URL
//                ,"Authorization", ApiService.class);

        LoginCacheProviders providers = RxHelper.INSTANCE.createCache(CardApplication.getContext().getExternalCacheDir(),
                        LoginCacheProviders.class);

//        RxHelper.INSTANCE.setCache(providers.getLogin(service.getVerificationCode(phone) ,
//                new DynamicKey(phone), new EvictDynamicKey(false)),callBack,owner);

    }


}
