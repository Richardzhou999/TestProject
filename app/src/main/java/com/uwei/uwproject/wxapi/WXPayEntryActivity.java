package com.uwei.uwproject.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uwei.base.ActivityManager;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.commom.utils.ToastUtil;
import com.uwei.uwproject.constant.Constant;


/**
 * Create by ZhuYuCai
 * date on 2022-6-13
 * 微信支付回调
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
        Log.i("---WXPayActivity","初始化...");
    }

    @Override

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("---WXPayActivity",baseReq.openId+"--"+baseReq.transaction);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i("---WXPayActivity",baseResp.errCode+"---"+baseResp.errStr);
        switch (baseResp.errCode){
            case 0:
                String useId = SharedPrefUtils.INSTANCE.get("userId","");
                if(TextUtils.isEmpty(useId)) {

                }
                break;
            case -1:
            case -2:

                break;
            default:break;
        }
        finish();
    }
}
