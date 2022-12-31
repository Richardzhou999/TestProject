package com.uwei.commom.rx

import com.google.gson.GsonBuilder
import com.uwei.commom.annotation.RequestConverter
import com.uwei.commom.annotation.ResponseConverter
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.reflect.Type

/**
 * @Author Charlie
 * @Date 2022/8/22 14:50
 */
class JXConverterFactory private constructor(): Converter.Factory(){

    private var jsonFactory: Converter.Factory
    private var xmlFactory: Converter.Factory

    init {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()
        jsonFactory = GsonConverterFactory.create(gson)
        xmlFactory = SimpleXmlConverterFactory.createNonStrict()
    }

    companion object {
        fun create() = JXConverterFactory()
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<out Annotation>,
                                      methodAnnotations: Array<out Annotation>, retrofit: Retrofit)
    : Converter<*, RequestBody>? {
        for (annotation in methodAnnotations) {
            if (annotation is RequestConverter) {
                if (annotation.format == ConverterFormat.JSON) {
                    return jsonFactory.requestBodyConverter(
                            type,
                            parameterAnnotations,
                            methodAnnotations,
                            retrofit
                    )
                } else if (annotation.format == ConverterFormat.XML) {
                    return xmlFactory.requestBodyConverter(
                            type,
                            parameterAnnotations,
                            methodAnnotations,
                            retrofit
                    )
                }
            }
        }
        return jsonFactory.requestBodyConverter(
                type,
                parameterAnnotations,
                methodAnnotations,
                retrofit
        )
    }

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation is ResponseConverter) {
                if (annotation.format == ConverterFormat.JSON) {
                    return jsonFactory.responseBodyConverter(type, annotations, retrofit)
                } else if (annotation.format == ConverterFormat.XML) {
                    return xmlFactory.responseBodyConverter(type, annotations, retrofit)
                }
            }
        }
        return jsonFactory.responseBodyConverter(type, annotations, retrofit)
    }



}