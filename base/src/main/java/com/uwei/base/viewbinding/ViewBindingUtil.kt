package com.uwei.base.viewbinding

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * @Author Charlie
 * @Date 2022/11/22 15:18
 */
object ViewBindingUtil {

    private val bindMap: Map<Int, View> = HashMap()

    @JvmStatic
    fun <VB : ViewBinding> inflateWithGeneric(genericOwner: Any, layoutInflater: LayoutInflater): VB =
        withGenericBindingClass<VB>(genericOwner) { clazz ->
            clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
        }

    @JvmStatic
    fun <VB : ViewBinding> inflateWithGeneric(genericOwner: Any, parent: ViewGroup?): VB =
        inflateWithGeneric(genericOwner, LayoutInflater.from(parent?.context), parent, false)

    @JvmStatic
    fun <VB : ViewBinding> inflateWithGeneric(genericOwner: Any, layoutInflater: LayoutInflater,
                                              parent: ViewGroup?, attachToParent: Boolean): VB =
        withGenericBindingClass<VB>(genericOwner) { clazz ->
            clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
                .invoke(null, layoutInflater, parent, attachToParent) as VB
        }


    @JvmStatic
    fun onViewClick(genericOwner: Any) =  withGenericBindingClick(genericOwner,null)

    @JvmStatic
    fun onViewClick(genericOwner: Any,view: View) =  withGenericBindingClick(genericOwner,view)



    private fun <VB : ViewBinding> withGenericBindingClass(genericOwner: Any, block: (Class<VB>) -> VB): VB {
        var genericSuperclass = genericOwner.javaClass.genericSuperclass
        var superclass = genericOwner.javaClass.superclass
        if(superclass != null){
            if (genericSuperclass is ParameterizedType) {
                genericSuperclass.actualTypeArguments.forEach { type ->
                    if((type as Class<VB>).name.contains("Binding")) {
                        try {
                            return block.invoke(type)
                        } catch (e: NoSuchMethodException) {
                        } catch (e: ClassCastException) {
                        } catch (e: InvocationTargetException) {
                            var tagException: Throwable? = e
                            while (tagException is InvocationTargetException) {
                                tagException = e.cause
                            }
                            throw tagException
                                ?: IllegalArgumentException("ViewBinding generic was found, but creation failed.")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                if (genericSuperclass is ParameterizedType) {
                    genericSuperclass.actualTypeArguments.forEach { type ->
                        if(type.typeName.contains("Binding")) {
                            try {
                                return block.invoke(type as Class<VB>)
                            } catch (e: NoSuchMethodException) {
                            } catch (e: ClassCastException) {
                            } catch (e: InvocationTargetException) {
                                var tagException: Throwable? = e
                                while (tagException is InvocationTargetException) {
                                    tagException = e.cause
                                }
                                throw tagException
                                    ?: IllegalArgumentException("ViewBinding generic was found, but creation failed.")
                            }
                        }
                    }
                }
            }
        }
        throw IllegalArgumentException("There is no generic of ViewBinding.")
    }

    private fun withGenericBindingClick(genericOwner: Any, root: View?){
        genericOwner.javaClass.methods.let { methods ->
            for (method in methods) {
                if(method.isAnnotationPresent(OnClick::class.java)){
                    if(method.genericReturnType != Void.TYPE){
                        throw IllegalArgumentException("The return value class must be void.")
                    }
                    method.getAnnotation(OnClick::class.java)?.value?.let { ClickArray->
                        for(click in ClickArray){
                            if (bindMap.containsKey(click)) {
                                bindMap[click]?.let { view ->
                                    bindOnClickEvent(genericOwner as Activity,method,view)
                                }
                            } else {
                                when(genericOwner){
                                    is Activity ->{
                                        genericOwner.findViewById<View>(click)?.let { view->
                                            bindOnClickEvent(genericOwner, method, view)
                                        }
                                    }
                                    is Fragment ->{
                                        var view = root?.findViewById<View>(click)
                                        if(view != null){
                                            bindOnClickEvent(genericOwner, method, view)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindOnClickEvent(instance: Any,method: Method,view: View){
        if(!method.isAccessible){
            method.isAccessible = true
        }
        view.setOnClickListener {
            try {
                method.invoke(instance,view);
            } catch (e: IllegalAccessException ) {
                e.printStackTrace();
            } catch (e: InvocationTargetException ) {
                e.printStackTrace();
            }
        }
    }




}