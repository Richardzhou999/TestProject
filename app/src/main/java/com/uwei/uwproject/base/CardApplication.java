package com.uwei.uwproject.base;

import android.app.Application;
import android.content.Context;


import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import com.uwei.commom.utils.CrashHandler;
import com.uwei.commom.utils.SharedPrefUtils;

import java.util.HashMap;


/**
 * @author Charlie
 */
public class CardApplication extends Application {

    private static CardApplication mInstance;
    private static Context mContext;

    public static CardApplication getInstance() {
        if (null == mInstance) {
            mInstance = new CardApplication();
        }
        return mInstance;

    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        init();

    }

    private void init() {
        SharedPrefUtils.INSTANCE.getInstance(getContext(),"zjtx");
        CrashHandler.getInstance().init(getContext());
        QbSdk.initX5Environment(getContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
            }
            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            @Override
            public void onViewInitFinished(boolean isX5) {}
        });
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);



    }


}
