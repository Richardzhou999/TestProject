package com.uwei.commom.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import com.uwei.commom.R


/**
 * @Author Charlie
 * @Date 2022/8/2 11:59
 */
object ToastUtil{

    /**
     * Toast单例
     */
    private var toast: Toast? = null

    /**
     * 图标状态 不显示图标
     */
    private val TYPE_HIDE = -1

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    fun showText(context: Context?, text: CharSequence?) {
        showToast(context, text,0, LENGTH_SHORT, TYPE_HIDE)
    }

    fun showText(context: Context?, resId: Int){
        showToast(context, context?.getString(resId),0, LENGTH_SHORT, TYPE_HIDE)
    }

    /**
     * 显示一个纯文本吐司带时间
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param time    持续的时间
     */
    fun showText(context: Context?, text: CharSequence?, time: Int) {
        showToast(context, text,0, time, TYPE_HIDE)
    }

    /**
     * 只显示带图标的吐司
     *
     * @param context   上下文
     * @param resId     显示的图片
     * @param time      持续的时间
     */
    fun showText(context: Context?, resId: Int, time: Int) {
        showToast(context, null ,resId, time,0)
    }

    /**
     * 隐藏当前Toast
     */
    fun cancelToast() {
        toast?.cancel()
    }


    /**
     * 初始化Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private fun initToast(context: Context?,text: CharSequence?,resId: Int,imgType: Int) {
        try {
            if(toast == null){
                toast = Toast.makeText(context, null, LENGTH_SHORT)
            }
            val layout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null)
            val toastImage = layout.findViewById(R.id.toast_img) as ImageView
            val toastTXT = layout.findViewById(R.id.toast_text) as TextView

            text?.let {
                toastTXT.text = text
            }
            if (imgType == TYPE_HIDE) {
                toastImage.visibility = View.GONE
            } else {
                if(resId != 0){
                    toastImage.setImageResource(resId)
                }
                toastImage.visibility = View.VISIBLE
                // 动画
                //ObjectAnimator.ofFloat(toast_img, "rotationY", 0, 360).setDuration(1700).start()
            }
            toast?.view = layout
            toast?.setGravity(Gravity.CENTER, 0, 70)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     * @param time    显示时长
     * @param imgType 图标状态
     */
    private fun showToast(context: Context?, text: CharSequence?,resId: Int, time: Int, imgType: Int) {
        initToast(context, text,resId,imgType)
        if (time == LENGTH_LONG) {
            toast?.duration = LENGTH_LONG
        } else {
            toast?.duration = LENGTH_SHORT
        }
        toast?.show()
    }
}