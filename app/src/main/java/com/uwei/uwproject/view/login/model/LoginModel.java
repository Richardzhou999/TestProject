package com.uwei.uwproject.view.login.model;

import androidx.lifecycle.LifecycleOwner;

import com.github.richard.core.EvictDynamicKey;
import com.github.richard.runtime.rx_cache3.internal.DynamicKey;
import com.uwei.base.mvp.BaseModel;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.rx.DefaultDialogCallBack;
import com.uwei.commom.rx.RxHelper;
import com.uwei.uwproject.base.CardApplication;
import com.uwei.uwproject.constant.Constant;
import com.uwei.uwproject.network.ApiService;
import com.uwei.uwproject.view.login.LoginCacheProviders;
import com.uwei.uwproject.view.login.mvp.LoginContract;



/**
 * @Author Charlie
 * @Date 2022/7/27 17:47
 */
public class LoginModel extends BaseModel implements LoginContract.LoginModel {

    @Override
    public void login(String phone, String number, DefaultDialogCallBack callBack, LifecycleOwner owner) {
        ApiService service = RxHelper.INSTANCE.getApiServer(CardApplication.getContext(), Constant.BASE_URL
                ,"Authorization", ApiService.class);


        RxHelper.INSTANCE.createSubscribe(service.login(phone, number),callBack,owner);
    }

    @Override
    public void getVerificationCode(String phone, DefaultBackCallBack callBack, LifecycleOwner owner) {

        ApiService service = RxHelper.INSTANCE.getApiServer(CardApplication.getContext(), Constant.BASE_URL
                ,"Authorization", ApiService.class);

        LoginCacheProviders providers = RxHelper.INSTANCE.createCache(CardApplication.getContext().getExternalCacheDir(),
                        LoginCacheProviders.class);

        RxHelper.INSTANCE.setCache(providers.getLogin(service.getVerificationCode(phone) ,
                new DynamicKey(phone), new EvictDynamicKey(false)),callBack,owner);

    }
}
