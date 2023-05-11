package com.uwei.uwproject.view.payment.model;

import com.google.gson.Gson;

import com.uwei.base.mvp.IBaseModel;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.uwproject.base.BaseActivity;
import com.uwei.uwproject.bean.PaymentBean;
import com.uwei.uwproject.constant.Constant;

/**
 * @Author Charlie
 * @Date 2022/7/26 11:38
 */
public class PayModelImplI implements PayModel, IBaseModel {


    public PayModelImplI(BaseActivity activity) {
    }

    @Override
    public void Payment(String itemId,String productId,String goodType,String goodDetail, int payMode,IModel model) {

        String ICCID = SharedPrefUtils.INSTANCE.get("iccid","");
        PaymentBean bean = new PaymentBean();
        if(payMode == 0){
            bean.setPayType("wxpay_app");
            bean.setAppId(Constant.WECHAT_APP_ID);
        }else {
            bean.setPayType("alipay_app");
            bean.setAppId(Constant.ALIPAY_APP_ID);
        }
        bean.setUid(ICCID);
        bean.setItemId(itemId);
        bean.setIccid(ICCID);
        bean.setOperatorProductId(productId);
        bean.setRealName("-");
        bean.setUserPhone("-");
        bean.setProvince("-");
        bean.setCity("-");
        bean.setDistrict("-");
        bean.setAddressDetail("-");
        bean.setGoodsDetail(goodDetail);
        bean.setHandlingMethod(1);
        bean.setCardSn("-");
        bean.setGoodsType("zjtx"+goodType);


        Gson gson = new Gson();
        String json = gson.toJson(bean);


//        addActSubscribe(apiServer.payment(body),new DefaultBackCallBack<String>() {
//
//            @Override
//            public void onSuccessEmpty() {
//
//            }
//
//            @Override
//            public void onSuccess(String baseResponse) {
//                SharedPrefUtils.INSTANCE.put("select_productId",productId);
//                SharedPrefUtils.INSTANCE.put("goodDetail",goodDetail);
//                model.paySuccess();
//                if(payMode == 0){
//                    PayUtil.payByWechat(CardApplication.getContext(),baseResponse);
//                }else {
//                    PayUtil.payByAli(CardApplication.getContext(),baseResponse);
//                }
//            }
//
//            @Override
//            public void onError(@NonNull BasicResponse<String> response) {
//                model.payError(response.getMsg());
//            }
//
//            @Override
//            public void onFailure(Throwable e, boolean netWork) throws Exception {
//
//            }
//        });




    }
}
