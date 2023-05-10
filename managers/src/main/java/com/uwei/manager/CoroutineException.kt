package com.uwei.manager

import kotlinx.coroutines.CoroutineExceptionHandler

/**
 *   @author : Aleyn
 *   time   : 2019/11/12
 */
object CoroutineException {

    private val defNetException = CoroutineExceptionHandler { _, throwable ->
        val exception = ExceptionHandle.handleException(throwable)

    }

    val netException: CoroutineExceptionHandler
        get() = customNetException ?: defNetException


    private var customNetException: CoroutineExceptionHandler? = null


    fun setNetException(netException: CoroutineExceptionHandler) = apply {
        customNetException = netException
    }

}