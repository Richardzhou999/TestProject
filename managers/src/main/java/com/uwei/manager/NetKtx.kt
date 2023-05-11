package com.uwei.base.mvvm



import com.uwei.manager.*
import kotlinx.coroutines.flow.*

/**
 * Flow 转换结果
 */
fun <T> Flow<IResponse<T>>.asResponse(): Flow<T> = transform { value ->
    return@transform if (value.isSuccess() && value.data() != null) {
        emit(value.data())
    } else if (value.isSuccess() && value.data() == null) {
        throw ResponseThrowable(ERROR.DATA_NULL)
    } else throw ResponseThrowable(value)
}

/**
 * 转换结果(只在成功后做处理，不关心 data 值)
 */
fun <T> Flow<IResponse<T>>.asSuccess(): Flow<T> = transform { value ->
    return@transform if(value.isSuccess()){
        emit(value.data())
    }else throw ResponseThrowable(value)
}

/**
 * 绑定Loading
 */
fun <T> Flow<T>.bindLoading(model: IViewModel, text: String = "") = onStart {
    model.showLoading(text)
}.onCompletion {
    model.dismissLoading()
}






