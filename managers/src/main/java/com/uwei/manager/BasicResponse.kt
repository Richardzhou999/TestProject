package com.uwei.manager

/**
 * @Author Charlie
 * @Date 2022/8/11 15:57
 */
data class BasicResponse<T> (
     val msg: String,
     val code: Int,
     val data: T,
     val result: T,
     val datas: T
) :IResponse<T>{
     override fun code() = code

     override fun msg() = msg

     override fun data() = data

     override fun isSuccess() = code == 200

}