package com.uwei.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.jaeger.library.StatusBarUtil
import com.uwei.base.mvp.ProxyFragment
import com.uwei.base.viewbinding.FragmentBinding
import com.uwei.base.viewbinding.FragmentBindingDelegate
import com.uwei.manager.IBaseView

/**
 * @Author Charlie
 * @Date 2022/7/19 14:12
 */
abstract class UWBaseFragment<VB : ViewBinding>: Fragment(), FragmentBinding<VB> by FragmentBindingDelegate()
    , IBaseView {

    private var mProxyFragment: ProxyFragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        StatusBarUtil.setLightMode(activity)
        createViewWithBinding(inflater,container)
        mProxyFragment = ProxyFragment(this)
        mProxyFragment?.bindPresenter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mProxyFragment?.unBindPresenter()
    }


    override fun getViewContext(): Context? {
        return context
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化相关参数
     *
     * @param bundle 参数bundle
     * @return 如果参数正确返回true, 错误返回false
     */
    protected fun initArgs(bundle: Bundle?) {}

    /**
     * 返回键触发时调用
     *
     * @return 返回true代表我已处理返回逻辑，Activity不用自己finish。
     */
    fun onBackPressed(): Boolean {
        return false
    }
}