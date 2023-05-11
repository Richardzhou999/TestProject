package com.uwei.uwproject.view.home;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.uwei.base.UWBaseFragment;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.uwproject.R;
import com.uwei.uwproject.databinding.FragmentHomeBinding;

/**
 * @Author Charlie
 * @Date 2022/7/19 11:21
 * 首页
 */
public class HomeFragment extends UWBaseFragment<FragmentHomeBinding> implements View.OnClickListener {

    private TextView loginInto;
    private ImageView into,service;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initView(View root) {
        StatusBarUtil.setTransparentForImageView(getActivity(),root.findViewById(R.id.ll_home));


        service = root.findViewById(R.id.iv_home_service);
        service.setOnClickListener(this);
        ImageView cardActivation = root.findViewById(R.id.card_activation);
        cardActivation.setOnClickListener(this);
        ImageView rechargePayment = root.findViewById(R.id.recharge_payment);
        rechargePayment.setOnClickListener(this);
        ImageView balanceQuery = root.findViewById(R.id.balance_query);
        balanceQuery.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        String phone = SharedPrefUtils.INSTANCE.get("phone","");
        getBinding().ivLoginInto.setVisibility(TextUtils.isEmpty(phone)?View.VISIBLE:View.GONE);
        getBinding().txtTelphone.setText(TextUtils.isEmpty(phone)?getString(R.string.login_into):phone);
    }

    private void updateVersion() {


    }

    private void test(){


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_telphone:{
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
                updateVersion();
                break;
            }
            case R.id.iv_home_service:{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "4008631678");
                intent.setData(data);
                startActivity(intent);
                break;
            }
            case R.id.card_activation:{



                break;
            }
            case R.id.recharge_payment:{

                break;
            }
            case R.id.balance_query:{

                break;
            }
            default:break;
        }
    }

}