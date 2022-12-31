package com.uwei.uwproject.view.mine

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.uwei.base.viewbinding.BindingViewHolder
import com.uwei.base.viewbinding.ViewBindingUtil.inflateWithGeneric

/**
 * @Author Charlie
 * @Date 2022/11/25 17:46
 */
abstract class BaseAdapter<T : Any, VB : ViewBinding> protected constructor(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
    @RequiresApi(api = Build.VERSION_CODES.P)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<VB> {
        return BindingViewHolder<VB>(inflateWithGeneric<VB>(this, parent))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        convert(holder,currentList[position])
    }

    abstract fun convert(holder: BindingViewHolder<VB>,item: T)

}