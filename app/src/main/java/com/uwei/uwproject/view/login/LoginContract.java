package com.uwei.uwproject.view.login;

import androidx.lifecycle.LifecycleOwner;

import com.uwei.base.mvp.IBasePresenter;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.rx.DefaultDialogCallBack;
import com.uwei.manager.IBaseView;

/**
 * @Author Charlie
 * @Date 2022/8/29 16:04
 */
public interface LoginContract {

    interface LoginModel{
        void login(String phone, String number, DefaultDialogCallBack callBack, LifecycleOwner owner);

        void getVerificationCode(String phone, DefaultBackCallBack callBack, LifecycleOwner owner);
    }

    interface LoginPresenter extends IBasePresenter{
        void login(String phone,String number);

        void getVerificationCode(String phone);
    }

    interface LoginView extends IBaseView{
        void intoView(int index,double value);
    }


}
