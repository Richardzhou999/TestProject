package com.uwei.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.uwei.base.mvvm.BaseViewModel
import com.uwei.manager.widget.LoadingDialog
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<VM : BaseViewModel, VB : ViewDataBinding> : AppCompatActivity() {

     lateinit var binding: VB
     lateinit var viewModel: VM
     private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        createViewModel()
        inject()
        //lifecycle.addObserver(viewModel)
        registerDefUIChange()
    }

    private fun initDataBinding() {

        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this
        loadingDialog = LoadingDialog(this)
    }

    private fun registerDefUIChange() {
        viewModel.defUI.showDialog.observe(this) {
            showLoading()
        }
        viewModel.defUI.dismissDialog.observe(this) {
            dismissLoading()
        }
    }

    private fun showLoading(){
        loadingDialog?.show()
    }

    private fun dismissLoading(){
        loadingDialog?.dismiss()
    }



    /**
     * 初始化DataBinding 和 Dagger2依赖注入
     */
    protected abstract fun inject();

    /**
     * 传入布局文件
     * @return 基类会自动生成对应的DataBinding供导出类使用
     */
    protected abstract fun getLayoutRes(): Int;


    override fun onDestroy() {
        super.onDestroy()
        if(binding != null){
            binding.unbind()
        }
    }

    /**
     * 创建 ViewModel
     *
     * 共享 ViewModel的时候，重写  Fragment 的 getViewModelStore() 方法，
     * 返回 activity 的  ViewModelStore 或者 父 Fragment 的 ViewModelStore
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(this)[tClass] as VM
        }
    }

}