package com.uwei.uwproject.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uwei.uwproject.bean.WechatBean;
import com.uwei.uwproject.constant.Constant;
import com.uwei.uwproject.view.AlipayActivity;

/**
 * @Author Charlie
 * @Date 2022/7/22 14:42
 */
public class PayUtil {

    /**
     * 微信支付
     * @param mContext
     * @param orderInfo
     */
    public static void payByWechat(Context mContext, String orderInfo){
        Gson gson = new Gson();
        WechatBean bean = gson.fromJson(orderInfo, WechatBean.class);

        IWXAPI api = WXAPIFactory.createWXAPI(mContext, Constant.WECHAT_APP_ID);
        api.registerApp(Constant.WECHAT_APP_ID);
        PayReq payRequest = new PayReq();
        payRequest.appId = bean.appId;
        payRequest.partnerId = bean.partnerId;
        payRequest.prepayId = bean.prepayId;
        payRequest.packageValue = "Sign=WXPay";
        payRequest.nonceStr = bean.nonceStr;
        payRequest.timeStamp = bean.timeStamp;
        payRequest.sign = bean.sign ;
        api.sendReq(payRequest);
    }


    /**
     * 支付宝支付
     * @param mContext
     * @param orderInfo
     */
    public static void payByAli(Context mContext,String orderInfo){
        Intent intent = new Intent(mContext, AlipayActivity.class);
        intent.putExtra("order",orderInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }



}
