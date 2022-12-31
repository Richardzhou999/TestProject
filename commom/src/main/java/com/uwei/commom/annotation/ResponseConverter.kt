package com.uwei.commom.annotation

import com.uwei.commom.rx.ConverterFormat

/**
 * @Author Charlie
 * @Date 2022/8/22 14:26
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ResponseConverter(val format: ConverterFormat = ConverterFormat.JSON)