package com.uwei.uwproject.view.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;


import com.uwei.base.UWBaseActivity;
import com.uwei.base.mvp.InjectPresenter;

import com.uwei.base.viewbinding.OnClick;
import com.uwei.commom.utils.PhoneUtils;
import com.uwei.commom.utils.ToastUtil;
import com.uwei.commom.utils.VerifyCodeTimeDown;
import com.uwei.uwproject.R;
import com.uwei.uwproject.databinding.ActivityLoginBinding;
import com.uwei.uwproject.view.login.presenter.LoginPresenter;


/**
 * @Author Charlie
 * @Date 2022/7/20 15:12
 * 登录-查询
 */
public class LoginActivity extends UWBaseActivity<ActivityLoginBinding> implements LoginContract.LoginView {


    @InjectPresenter
    private LoginPresenter presenter;

    private Object[] value = new Object[5];

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        binding.llLoginService.loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.loginNumber.getText().length() == 11 && s.toString().length() > 0){
                    binding.llLoginService.loginServiceBtn.setEnabled(true);
                    binding.llLoginService.loginServiceBtn.setBackgroundResource(R.drawable.bg_login_btn);
                }else {
                    binding.llLoginService.loginServiceBtn.setEnabled(false);
                    binding.llLoginService.loginServiceBtn.setBackgroundResource(R.drawable.bg_un_login_btn);
                }
            }
        });
        binding.llLoginPhone.loginCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.loginNumber.getText().length() == 11 && s.toString().length() > 0){
                    binding.llLoginPhone.loginPhoneBtn.setEnabled(true);
                    binding.llLoginPhone.loginPhoneBtn.setBackgroundResource(R.drawable.bg_login_btn);
                }else {
                    binding.llLoginPhone.loginPhoneBtn.setEnabled(false);
                    binding.llLoginPhone.loginPhoneBtn.setBackgroundResource(R.drawable.bg_un_login_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.verify_code, R.id.login_mode_service,R.id.login_mode_phone,
            R.id.login_service_forget,R.id.login_service_btn,R.id.login_phone_btn})
    public void ViewClick(View v) {
        switch (v.getId()){
            case R.id.verify_code:{
                if(!TextUtils.isEmpty(binding.loginNumber.getText().toString())){
                    VerifyCodeTimeDown timeDown = new VerifyCodeTimeDown(LoginActivity.this
                            ,60000,1000);
                    timeDown.setTextView(binding.llLoginPhone.verifyCode);
                    timeDown.setBackGroundColor(R.drawable.bg_no_code_text,R.drawable.bg_code_text);
                    timeDown.startNow();
                    presenter.getVerificationCode(binding.loginNumber.getText().toString());
                    break;
                }else {
                    ToastUtil.INSTANCE.showText(LoginActivity.this,getString(R.string.mobile_empty));
                }

            }
            case R.id.login_mode_service:{
                binding.llLoginPhone.getRoot().setVisibility(View.VISIBLE);
                binding.llLoginService.getRoot().setVisibility(View.GONE);
                break;
            }
            case R.id.login_mode_phone:{
                binding.llLoginPhone.getRoot().setVisibility(View.GONE);
                binding.llLoginService.getRoot().setVisibility(View.VISIBLE);
                break;
            }
            case R.id.login_service_forget:{
                break;
            }
            case R.id.login_phone_forget:{
                break;
            }

            case R.id.login_service_btn:{

                if(PhoneUtils.isMobilPhone(binding.loginNumber.getText().toString())){


                    presenter.login(binding.loginNumber.getText().toString(),
                            binding.llLoginService.loginPassword.getText().toString());
                }else {
                    ToastUtil.INSTANCE.showText(LoginActivity.this,getString(R.string.mobile_format));
                }
                break;
            }
            case R.id.login_phone_btn:{

                if(PhoneUtils.isMobilPhone(binding.loginNumber.getText().toString())){

                    presenter.login(binding.loginNumber.getText().toString(),
                            binding.llLoginPhone.loginCode.getText().toString());
                }else {
                    ToastUtil.INSTANCE.showText(LoginActivity.this,getString(R.string.mobile_format));
                }
                break;
            }
            default:break;
        }
    }

    @Override
    public void intoView(int index,double value) {
       // startActivity(new Intent(LoginActivity.this,MainActivity.class));

    }

}