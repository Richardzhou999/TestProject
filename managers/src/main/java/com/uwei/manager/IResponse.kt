package com.uwei.manager

interface IResponse<T>{

    fun code(): Int
    fun msg(): String
    fun data(): T
    fun isSuccess(): Boolean

}