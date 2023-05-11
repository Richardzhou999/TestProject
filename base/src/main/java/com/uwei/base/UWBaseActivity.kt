package com.uwei.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.uwei.base.mvp.ProxyActivity
import com.uwei.base.viewbinding.ViewBindingUtil
import com.uwei.manager.IBaseView
import com.uwei.manager.LoadView


/**
 * @Author Charlie
 * @Date 2022/7/19 11:21
 */
abstract class UWBaseActivity<VB : ViewBinding>: AppCompatActivity() , IBaseView {

    //两次点击按钮之间的间隔，目前为1000ms
    val MIN_CLICK_DELAY_TIME = 1000

    lateinit var binding: VB
    private var mProxyActivity: ProxyActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //activity管理
        ActivityManager.addActivity(this)
        //设置布局
        binding = ViewBindingUtil.inflateWithGeneric(this,layoutInflater)
        setContentView(binding.root)
        ViewBindingUtil.onViewClick(this)

        mProxyActivity = ProxyActivity(this)
        mProxyActivity?.bindPresenter()

        //设置数据
        initData()
        //设置事件
        initEvent()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun getViewContext(): Context? {
        return this
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        mProxyActivity?.unBindPresenter()
        //activity管理
        ActivityManager.removeActivity(this)
    }


    /**
     * 设置数据
     */
    protected abstract fun initData()

    /**
     * 设置事件
     */
    protected abstract fun initEvent()

    fun intoActivity(intoActivity: Activity){
        val intent = Intent(this,intoActivity::class.java);
        startActivity(intent)
    }

    /**
     * 跳转页面
     */
    fun intoActivity(clz: Class<*>?){
        val intent = Intent(this,clz);
        startActivity(intent)
        ActivityManager.removeActivity(this)
    }

    /**
     * 带参跳转页面
     */
    fun intoActivity(clz: Class<*>?,bundle: Bundle){
        val intent = Intent(this,clz);
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null && null != imm) {
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    /**
     * 显示软键盘
     */
    fun showSoftInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null && null != imm) {
            imm.showSoftInputFromInputMethod(currentFocus!!.windowToken, 0)
        }
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    abstract inner class OnSingleClickListener : View.OnClickListener {
        private var lastClickTime: Long = 0
        abstract fun onSingleClick(view: View?)
        override fun onClick(view: View) {
            val curClickTime = System.currentTimeMillis()
            if (curClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime
                onSingleClick(view)
            }
        }
    }


    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = (left
                    + v.getWidth())
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false
    }
}