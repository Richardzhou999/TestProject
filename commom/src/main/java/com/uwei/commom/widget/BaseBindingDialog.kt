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

@file:Suppress("unused")

package com.uwei.commom.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.uwei.commom.R


/**
 * How to modify the base class to use view binding, you need the following steps:
 * 1. Adds a generic of view binding to the base class.
 * 2. Declares a binding object.
 * 3. Uses [inflateBindingWithGeneric] method to create the binding object.
 * 4. Uses the root of the binding object instead of layout id to set content view.
 *
 * Here is the core code.
 *
 * @author
 */
abstract class BaseBindingDialog(context: Context) : Dialog(context) {

  init {

    setContentView(R.layout.dialog_loading)
    setCancelable(false)
    setCanceledOnTouchOutside(false)

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//    window?.requestFeature(Window.FEATURE_NO_TITLE)
//    window?.setFlags(
//      WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//      WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//    )

//    window?.setDimAmount(0f)



  }
}