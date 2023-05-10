package com.uwei.manager

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.LifecycleOwner

/**
 * @Author Charlie
 * @Date 2022/8/29 9:23
 */
interface IBaseView {

    fun getViewContext(): Context?

    fun getLifecycleOwner(): LifecycleOwner?

    fun getDialog(): Dialog?

    /*showLoading*/
    fun showLoading() {}

    /*发生错误时的弹窗*/
    fun showError(content: String?) {}

    fun dismissLoading() {}

    /*showContent*/
    fun showContent() {}

    /*showNoData*/
    fun showNoData() {}

    /*showNoNet*/
    fun showNoNet() {}

    /*释放dialog资源*/
    fun destroy() {}

    //显示未登录对话框
    fun showNotRegiestAsert() {}

}