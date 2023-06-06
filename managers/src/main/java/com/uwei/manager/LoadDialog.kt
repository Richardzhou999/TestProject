package com.uwei.manager

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import android.os.Bundle
import android.view.View

/**
 * 带提示内容的加载框
 */
class LoadDialog(context: Context, private val mContent: String) :Dialog(context, R.style.DialogStyle) {

    private lateinit var mProgressView: ProgressImageView
    private lateinit var mTvContent: TextView

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress_content)
        mProgressView = findViewById(R.id.view_list_empty_progress)
        mTvContent = findViewById(R.id.tv_content)
        mTvContent.text = mContent
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mProgressView.visibility = View.VISIBLE

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mProgressView.visibility = View.GONE
    }
}