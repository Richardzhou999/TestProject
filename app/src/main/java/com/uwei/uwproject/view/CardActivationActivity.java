package com.uwei.uwproject.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uwei.uwproject.R;
import com.uwei.uwproject.view.login.LoginActivity;

public class CardActivationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView status,cardTXT,cardTips,cardTipsTitle,btnCard;
    private View view1,view2;
    private ImageView cardImage;
    private Boolean writeStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_activation);
        initView();
        initData();
    }

    protected void initView() {

        status = findViewById(R.id.tv_card_status);
        cardTXT = findViewById(R.id.tv_card);
        cardImage = findViewById(R.id.iv_card);
        cardTipsTitle = findViewById(R.id.card_tips_title);
        cardTips =  findViewById(R.id.tv_card_tips);
        btnCard = findViewById(R.id.btn_card);
        view1 = findViewById(R.id.card_view1);
        view2 = findViewById(R.id.card_view2);
    }

    protected void initData() {

        writeStatus = getIntent().getBooleanExtra("writeSuccess",false);
        status.setText(writeStatus?R.string.activation_success:R.string.activation_error);
        cardImage.setImageResource(writeStatus?R.mipmap.icon_activation_success:R.mipmap.icon_activation_error);
        if(writeStatus) {
            String phone = getIntent().getStringExtra("phone");
            cardTXT.setVisibility(View.VISIBLE);
            cardTXT.setText(getString(R.string.activation_success_phone, phone));
            cardTips.setVisibility(View.VISIBLE);
            cardTipsTitle.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        }
        btnCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(writeStatus){
            intent = new Intent(CardActivationActivity.this, LoginActivity.class);
        }else {
            
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}