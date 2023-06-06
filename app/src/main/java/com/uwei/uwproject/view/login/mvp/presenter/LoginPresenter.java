package com.uwei.uwproject.view.login.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uwei.base.mvp.BasePresenter;
import com.uwei.manager.BasicResponse;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.rx.DefaultDialogCallBack;
import com.uwei.commom.utils.CHLog;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.commom.utils.ToastUtil;
import com.uwei.uwproject.base.CardApplication;
import com.uwei.uwproject.bean.LoginBean;
import com.uwei.uwproject.view.login.mvp.LoginContract;
import com.uwei.uwproject.view.login.model.LoginModel;
import com.uwei.uwproject.view.payment.presenter.IView;

import java.util.Objects;

/**
 * @Author Charlie
 * @Date 2022/7/27 17:53
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView, LoginModel> implements LoginContract.LoginPresenter{

    @Override
    public void login(String phone, String number) {

        getModel().login(phone,number, new DefaultDialogCallBack<LoginBean>(mView.getViewContext(),mView) {
            @Override
            public void onSuccessEmpty() {}
            @Override
            public void onSuccess(LoginBean response) {
                getView().intoView(0,0);
            }

            @Override
            public void onError(BasicResponse<LoginBean> response) {
                ToastUtil.INSTANCE.showText(CardApplication.getContext(),response.getMsg());
                getView().intoView(0,0);
            }

            @Override
            public void onFailure(Throwable e, boolean netWork) throws Exception {}

        },getView().getLifecycleOwner());
    }

    @Override
    public void getVerificationCode(String phone) {
        Objects.requireNonNull(getModel()).getVerificationCode(phone, new DefaultBackCallBack(getView().getViewContext()) {

            @Override
            public void onSuccessEmpty() {
                CHLog.e("onSuccessCode");
            }

            @Override
            public void onFailure(@Nullable Throwable e, boolean netWork) throws Exception {}

            @Override
            public void onError(@NonNull BasicResponse response) {}

            @Override
            public void onSuccess(Object response) {
                CHLog.e("onSuccess");
            }
        }, getView().getLifecycleOwner());
    }
}
