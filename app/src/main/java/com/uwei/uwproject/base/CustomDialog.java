package com.uwei.uwproject.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.uwei.uwproject.R;


/**
 * @Author Charlie
 * @Date 2022/7/20 15:12
 */
public class CustomDialog extends Dialog {

    private Window mWindow;
    private TextView contentTXT,sure;
    private String content;
    private int layoutId;
    private ResultListener listener;
    public CustomDialog(@NonNull Context context,int layoutId, String content,ResultListener listener) {
        super(context);
        this.layoutId = layoutId;
        this.content = content;
        this.listener = listener;
    }

    public interface ResultListener{
        void getResult();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutId);
        //初始化界面控件
        initView();

        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void initView() {
        sure = findViewById(R.id.sure);
        contentTXT = findViewById(R.id.txt_content);
    }

    private void initData() {
        if(!TextUtils.isEmpty(content)){
            contentTXT.setText(content);
        }
    }

    private void initEvent() {
        if(sure != null){
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if(listener != null){
                         listener.getResult();
                         dismiss();
                     }
                }
            });
        }
    }

    public void setContentTXT(String str){
        contentTXT.setText(str);
    }

    public void setContent(CharSequence str){
        contentTXT.setText(str);
    }

    public void shows(){
        create();
        show();
    }



    /**
     * 自定义属性
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public  void setProperty(int x,int y,int w,int h){
        mWindow = getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        //设置对话框的位置，0为中间
        params.width = w;
        params.height = h;
        params.alpha = 1.0f;
        params.gravity = Gravity.CENTER;
        mWindow.setAttributes(params);
    }
}
