package com.uwei.uwproject.view.login.mvvm

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.uwei.base.BaseMvvmActivity
import androidx.lifecycle.lifecycleScope
import com.uwei.commom.utils.ToastUtil
import com.uwei.uwproject.R
import com.uwei.uwproject.bean.LoginBean
import com.uwei.uwproject.databinding.ActivityLogin2Binding
import com.uwei.uwproject.view.login.model.LoginMVModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class Login2Activity : BaseMvvmActivity<LoginMVModel,ActivityLogin2Binding>(){


    override fun inject() {
        binding.loginServiceBtn.setOnClickListener {
            viewModel.login(binding.loginNumber.text.toString())
        }

        lifecycleScope.launchWhenCreated {
            viewModel.mBanners.collect {

                binding.user = it
            }
        }



    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_login2
    }

}