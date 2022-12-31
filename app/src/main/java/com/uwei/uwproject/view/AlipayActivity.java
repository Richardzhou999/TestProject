package com.uwei.uwproject.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.uwei.base.ActivityManager;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.commom.utils.ToastUtil;
import com.uwei.uwproject.bean.PayResult;

import java.util.Map;

/**
 * @Author Charlie
 * @Date 2022/7/22 14:50
 * 支付宝支付页面
 */
public class AlipayActivity extends AppCompatActivity {
    private static final String TAG = "AlipayActivity";
    private String orderInfo = "";
    public final static int PAY_CODE = 0x06;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderInfo = getIntent().getStringExtra("order");
        aliPay();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case PAY_CODE:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String status = payResult.getResultStatus();
                    if(status.equals("9000")){
                        String useId = SharedPrefUtils.INSTANCE.get("userId","");
                        if(TextUtils.isEmpty(useId)) {

                        }
                    }else{

                    }
                    break;
                default:break;
            }
            finish();
        }
    };

    /**
     * 调起支付宝支付
     */
    private void aliPay(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"order:"+orderInfo);

                PayTask alipay = new PayTask(AlipayActivity.this);
                Map<String,String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = PAY_CODE;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(runnable);
        payThread.start();
    }
}
