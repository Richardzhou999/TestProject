package com.uwei.base.mvp

import com.uwei.manager.IBaseView
import java.lang.reflect.Field

/**
 * @Author Charlie
 * @Date 2022/8/29 15:04
 */
open class ProxyImpl(view: IBaseView?) : IProxy{

    private var mView: IBaseView? = view
    private var mInjectPresenters: MutableList<BasePresenter<*,*>>? = null

    init {
        mInjectPresenters = mutableListOf()
    }

    override fun bindPresenter() {

        //获得已经声明的变量，包括私有的
        val fields: Array<Field> = mView?.javaClass?.declaredFields as Array<Field>
        for (field in fields) {
            //获取变量上面的注解类型
            val injectPresenter = field.getAnnotation(InjectPresenter::class.java)
            if (injectPresenter != null) {
                try {
                    val type = field.type as Class<out BasePresenter<*, *>?>
                    val mInjectPresenter = type.newInstance()
                    mView?.let { view->
                        mInjectPresenter?.let {  presenter->
                            presenter.attachView(view)
                            field.isAccessible = true
                            field[mView] = mInjectPresenter
                            mInjectPresenters?.add(presenter)
                        }
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                    throw RuntimeException("SubClass must extends Class:BasePresenter")
                }
            }
           // val dialog = field.getAnnotation()
        }
    }

    /**
     * 解绑 presenter 的实现
     * 避免内存泄漏
     */
    override fun unBindPresenter() {
        mInjectPresenters?.let {
            for (presenter in it) {
                presenter.detachView()
            }
            it.clear()
            mInjectPresenters = null
        }
    }

}