package com.uwei.base.mvp

import com.uwei.manager.IBaseView
import java.lang.ref.SoftReference
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.math.BigDecimal

/**
 * @Author Charlie
 * @Date 2022/8/27 9:43
 */
abstract class BasePresenter<V: IBaseView,M: IBaseModel> : IBasePresenter {

    /**
     * 使用软引用的方式让 P 层持有 V 层的引用，并且提供了 getView() 方法给 P 层调用，
     * 父类 View 变量进行私有化，防止子类对其进行更改造成的其他错误。我们的 MainPresenter
     * 获取 Activity 的引用就可以使用 getView() 方法获得
     */
    private var mReferenceView: SoftReference<IBaseView>? = null
    lateinit var mView: V
    lateinit var mModel: M


    override fun attachView(view: IBaseView?) {
        mReferenceView = SoftReference(view)
        //动态代理获取view，做统一处理
        mView = view?.javaClass?.interfaces?.let { interfaces ->
            Proxy.newProxyInstance(view.javaClass.classLoader, interfaces) { proxy, method, args ->
                if (mReferenceView == null || mReferenceView?.get() == null) {
                    null
                } else if (args == null) {
                    method?.invoke(mReferenceView?.get())
                } else {
                    for (index in args.indices) {
                        when (args[index]) {
                            is Int -> args[index] as Int
                            is Float -> args[index] as Float
                            is Double -> args[index] as Double
                            is String -> args[index] as String
                            is BigDecimal -> args[index] as BigDecimal
                            else -> args[index]
                        }
                    }
                    when (args.size) {
                        1 -> method?.invoke(mReferenceView?.get(), args[0])
                        2 -> method?.invoke(mReferenceView?.get(), args[0], args[1])
                        else -> method?.invoke(mReferenceView?.get(), args[0], args[1], args[2])
                    }
                }
            }
        } as V
        //通过获得泛型类的父类，拿到泛型的接口实例，通过反射来实例化 model
        (this.javaClass.genericSuperclass as ParameterizedType).also { parameterizedType ->
            //获得父类的泛型参数的实际泛型类型列表
            val types = parameterizedType.actualTypeArguments;
            try {
                for(index in types.indices){
                    //判断当前model的父类是否为BaseModel(用于拓展泛型参数)
                    if(types[index].toString().contains("Model")){
                        mModel = (types[index] as Class<*>).newInstance() as M
                    }
                }
            } catch (e: IllegalAccessException ) {
                e.printStackTrace()
            } catch (e: InstantiationException ) {
                e.printStackTrace()
            } catch (e: RuntimeException){
                e.printStackTrace()
            }
        }
    }

    override fun detachView() {
        mReferenceView?.clear()
        mReferenceView = null
    }

    fun getModel(): M {
        return mModel
    }

    fun getView(): V {
        return mView
    }

}