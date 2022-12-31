package com.uwei.base.mvp

import com.uwei.manager.IBaseView

/**
 * @Author Charlie
 * @Date 2022/8/27 9:48
 */
interface IBasePresenter{

    // 绑定
    fun attachView(view: IBaseView?)

    // 解绑
    fun detachView()



}