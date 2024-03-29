package com.uwei.uwproject.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.uwei.base.ActivityManager;
import com.uwei.uwproject.R;

/**
 * @Author Charlie
 * @Date 2022/8/1 14:29
 */
public abstract class BaseMvpActivity extends com.uwei.base.BaseMvpActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(BaseMvpActivity.this,
                ContextCompat.getColor(BaseMvpActivity.this, R.color.white),0);
        ImageView back = findViewById(R.id.back);
        if(back != null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.INSTANCE.removeActivity(BaseMvpActivity.this);
                }
            });
        }
    }
}
