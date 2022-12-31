package com.uwei.base.mvp

/**
 * @Author Charlie
 * @Date 2022/8/29 15:04
 * 由于Java 单继承的特性，这里我们使用proxy 代理，来实现 BaseActivity 和 BaseFragment 重复代码的封装实现
 * 这里封装两个接口，一个绑定Presenter, 一个解绑Presenter
 */
interface IProxy {
    fun bindPresenter()
    fun unBindPresenter()
}