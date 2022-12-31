package com.uwei.uwproject.view.payment.presenter;

/**
 * @Author Charlie
 * @Date 2022/8/2 11:37
 */
public interface IView {

    void paySuccess();

    void payError(String error);
}
