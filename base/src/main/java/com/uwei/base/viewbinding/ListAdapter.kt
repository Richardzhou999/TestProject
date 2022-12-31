package com.uwei.base.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

inline fun <T, reified VB : ViewBinding> listAdapterxx(
  diffCallback: DiffUtil.ItemCallback<T>,
  crossinline onBindViewHolder: BindingViewHolder<VB>.(T) -> Unit
) = object : ListAdapter<T, BindingViewHolder<VB>>(diffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BindingViewHolder<VB>(parent)

  override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
    onBindViewHolder(holder, currentList[position])
  }
}

//inline fun <T, reified VB : ViewBinding> listAdapter(
//  diffCallback: DiffUtil.ItemCallback<T>,
//  noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
//  crossinline onBindViewHolder: com.dylanc.viewbinding.nonreflection.BindingViewHolder<VB>.(T) -> Unit
//) = object : ListAdapter<T, com.dylanc.viewbinding.nonreflection.BindingViewHolder<VB>>(diffCallback) {
//  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//    com.dylanc.viewbinding.nonreflection.BindingViewHolder(parent, inflate)
//
//  override fun onBindViewHolder(holder: com.dylanc.viewbinding.nonreflection.BindingViewHolder<VB>, position: Int) {
//    onBindViewHolder(holder, currentList[position])
//  }
//}