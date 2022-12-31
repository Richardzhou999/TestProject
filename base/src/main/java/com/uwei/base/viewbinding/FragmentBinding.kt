/*
 * Copyright (c) 2020. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwei.base.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.viewbinding.ViewBinding
import com.uwei.base.viewbinding.ViewBindingUtil

interface FragmentBinding<VB : ViewBinding> {
  val binding: VB
  fun Fragment.createViewWithBinding(inflater: LayoutInflater, container: ViewGroup?): View
}

class FragmentBindingDelegate<VB : ViewBinding> : FragmentBinding<VB> {
  private var mBinding: VB? = null


  override val binding: VB
    get() = requireNotNull(mBinding) { "The property of binding has been destroyed." }

  override fun Fragment.createViewWithBinding(inflater: LayoutInflater, container: ViewGroup?): View {
    if (mBinding == null) {
      mBinding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
      ViewBindingUtil.onViewClick(this, mBinding!!.root)
//      viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
//        override fun onDestroy(owner: LifecycleOwner) {
//          handler.post { _binding = null }
//        }
//      })
    }
    return mBinding!!.root
  }
}