package com.uwei.uwproject.view.payment.presenter;


import com.uwei.uwproject.base.BaseActivity;
import com.uwei.uwproject.view.payment.model.IModel;
import com.uwei.uwproject.view.payment.model.PayModel;
import com.uwei.uwproject.view.payment.model.PayModelImplI;

/**
 * @Author Charlie
 * @Date 2022/7/26 11:39
 */
public class PayPresenterImpl implements PayPresenter, IModel {

    private PayModel model;
    private IView view;

    public PayPresenterImpl(BaseActivity activity, IView view) {
        this.model = new PayModelImplI(activity);
        this.view = view;
    }

    @Override
    public void Payment(String itemId,String productId,String goodType,String goodDetail, int payMode) {
        model.Payment(itemId,productId,goodType,goodDetail,payMode,this);
    }

    @Override
    public void paySuccess() {
        view.paySuccess();
    }

    @Override
    public void payError(String error) {
        view.payError(error);
    }


}
