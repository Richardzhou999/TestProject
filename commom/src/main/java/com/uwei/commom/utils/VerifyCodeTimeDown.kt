package com.uwei.commom.utils

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * @Author Charlie
 * @Date 2022/7/27 14:52
 * 60秒倒计时
 */
class VerifyCodeTimeDown(val mContext: Context, millisInFuture: Long,
                         countDownInterval: Long)
    : CountDownTimer(millisInFuture, countDownInterval) {
    private var startColor = 0
    private var endColor = 0
    private var btGetVerifyCode: TextView? = null

    fun setBackGroundColor(startColor: Int,endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
    }

    fun setTextView(textView: TextView){
        btGetVerifyCode = textView
    }

    override fun onTick(millisUntilFinished: Long) {
        Log.e("CountDownTimer", "onTick = " + millisUntilFinished / 1000)
        btGetVerifyCode?.text = "${millisUntilFinished.div( 1000)}"
    }

    override fun onFinish() {
        btGetVerifyCode?.text = "重新获取"
        btGetVerifyCode?.isClickable = true
        if(endColor != 0){
            btGetVerifyCode?.background = ContextCompat.getDrawable(mContext, endColor)
        }

    }

    fun startNow() {
        if(startColor != 0){
            btGetVerifyCode?.background = ContextCompat.getDrawable(mContext, startColor)
        }
        if (btGetVerifyCode?.isClickable == true) {
            start()
        }
        btGetVerifyCode?.isClickable = false
    }
}