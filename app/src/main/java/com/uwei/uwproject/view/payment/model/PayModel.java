package com.uwei.uwproject.view.payment.model;

/**
 * @Author Charlie
 * @Date 2022/7/26 11:38
 */
public interface PayModel {

    void Payment(String itemId,String productId,String goodType,String goodDetail,int payMode,IModel model);

}
