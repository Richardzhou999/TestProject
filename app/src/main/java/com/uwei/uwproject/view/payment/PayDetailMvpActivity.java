package com.uwei.uwproject.view.payment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import com.uwei.commom.utils.AppInfoUtils;
import com.uwei.commom.utils.ToastUtil;
import com.uwei.uwproject.R;
import com.uwei.uwproject.base.BaseMvpActivity;
import com.uwei.uwproject.bean.PayDetailBean;
import com.uwei.uwproject.view.payment.presenter.IView;
import com.uwei.uwproject.view.payment.presenter.PayPresenter;
import com.uwei.uwproject.view.payment.presenter.PayPresenterImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class PayDetailMvpActivity extends BaseMvpActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, IView {

    private TextView toolbar,title,price,deduction,total;
    private TextView paymentBtn;
    private ImageView mealImage;
    private PayDetailBean payment;
    private PayPresenter presenter;
    private RadioGroup paymentGroup;
    private int paymentMode;
    private String productId;
    private double reduction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        payment = (PayDetailBean) getIntent().getSerializableExtra("payment");
        reduction = getIntent().getDoubleExtra("reduction",0.00);
        productId = getIntent().getStringExtra("productId");

        super.onCreate(savedInstanceState);
    }




    protected void initView() {
        toolbar = findViewById(R.id.title);
        title = findViewById(R.id.meal_detail_title);
        price = findViewById(R.id.monthly_rent_prepayment);
        deduction = findViewById(R.id.daily_deduction);
        total = findViewById(R.id.total_payment);
        paymentBtn = findViewById(R.id.btn_payment);
        mealImage = findViewById(R.id.iv_meal);
        paymentGroup = findViewById(R.id.radioGroup_mode);

    }

    @Override
    protected void initData() {
        toolbar.setText(getString(R.string.meal_detail));
        if(payment != null && reduction != 0){
            Glide.with(this)
                    .load(payment.getPicUrl())
                    .placeholder(ContextCompat.getDrawable(PayDetailMvpActivity.this,R.mipmap.icon_meal))
                    .into(mealImage);
            title.setText(payment.getName());
            price.setText(getString(R.string.yuan,payment.getUnitPrice()));
            deduction.setText(getString(R.string.yuan,reduction));
            BigDecimal totalPrice = payment.getUnitPrice().subtract(BigDecimal.valueOf(reduction));
            total.setText(getString(R.string.yuan,totalPrice));
        }
        presenter = new PayPresenterImpl(this,this);

    }

    @Override
    protected void initEvent() {
        paymentBtn.setOnClickListener(this);
        paymentGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {


        if(paymentMode == 0){
            if(!AppInfoUtils.INSTANCE.isAppInstalled(PayDetailMvpActivity.this,"com.tencent.mm")){
                ToastUtil.INSTANCE.showText(PayDetailMvpActivity.this,"您当前未安装微信");

                return;
            }
        }else {
            if(!AppInfoUtils.INSTANCE.isAppInstalled(PayDetailMvpActivity.this,"com.eg.android.AlipayGphone")){
                ToastUtil.INSTANCE.showText(PayDetailMvpActivity.this,"您当前未安装支付宝");

                return;
            }
        }

        if(payment.getUnitPrice().doubleValue() > 1){
            DecimalFormat decimalFormat = new DecimalFormat("0");
            presenter.Payment(payment.getId()+"",productId,decimalFormat.format(payment.getUnitPrice())
                    ,payment.getName(),paymentMode);
        }else {
            ToastUtil.INSTANCE.showText(PayDetailMvpActivity.this,"当前金额不能小于1元");

        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.radioButton_wechat:{
                    paymentMode = 0;
                    break;
                }
                case R.id.radioButton_alipay:{
                    paymentMode = 1;
                    break;
                }
                default:break;
            }
    }

    @Override
    public void paySuccess() {

    }

    @Override
    public void payError(String error) {

        ToastUtil.INSTANCE.showText(PayDetailMvpActivity.this,error);
    }




}