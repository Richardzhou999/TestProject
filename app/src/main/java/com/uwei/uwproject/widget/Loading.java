package com.uwei.uwproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.uwei.uwproject.R;


/**
 * @Author Charlie
 * @Date 2022/8/4 13:58
 */
public class Loading extends Dialog {




    public Loading(@NonNull Context context) {
        super(context);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading_1);
        //设置dialog属性
        //setCancelable(true);
        setCanceledOnTouchOutside(false);
        //背景透明处理
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().setDimAmount(0f);





    }



}
