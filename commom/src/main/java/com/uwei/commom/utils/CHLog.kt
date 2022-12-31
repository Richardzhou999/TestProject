package com.uwei.commom.utils

import android.os.Build
import android.util.Log
import com.uwei.commom.BuildConfig

/**
 * @Author Charlie
 * @Date 2022/8/6 15:24
 */
object CHLog {
    /**
     * Get The Current Function Name
     *
     * @return Name
     */
    private fun functionName(string: Any): String?{
        val sts = Thread.currentThread().stackTrace ?: return null
        for (st in sts) {
            if (st.isNativeMethod) {
                continue
            }
            if (st.className == Thread::class.java.name) {
                continue
            }
            if (st.className == this.javaClass.name) {
                continue
            }
            var buffer = StringBuffer();
            buffer.append("[ ")
                    .append(Thread.currentThread().name)
                    .append(": ")
                    .append(st.methodName)
                    .append("(")
                    .append(st.fileName)
                    .append(":")
                    .append(st.lineNumber)
                    .append(") ]:")
                    .append(string);
            return buffer.toString()
        }
        return null
    }



    /**
     * TAG 名称
     */
    private const val tag = "UWLog"
    @JvmStatic
    fun i(str: Any) {
        print(Log.INFO, str)
    }

    @JvmStatic
    fun d(str: Any) {
        print(Log.DEBUG, str)
    }

    @JvmStatic
    fun v(str: Any) {
        print(Log.VERBOSE, str)
    }

    @JvmStatic
    fun w(str: Any) {
        print(Log.WARN, str)
    }

    @JvmStatic
    fun e(str: Any) {
        print(Log.ERROR, str)
    }



    private fun isDEBUG(): Boolean{
        return BuildConfig.DEBUG
    }

    /**
     * 用于区分不同接口数据 打印传入参数
     *
     * @param index
     * @param str
     */
    private fun print(index: Int, str: Any) {

//        if (!isDEBUG()) {
//            return
//        }

        // Close the debug log When DEBUG is false

        when (index) {
            Log.VERBOSE -> Log.v(tag, functionName(str))
            Log.DEBUG -> Log.d(tag, functionName(str))
            Log.INFO -> Log.i(tag, functionName(str))
            Log.WARN -> Log.w(tag, functionName(str))
            Log.ERROR -> Log.e(tag, functionName(str))
            else -> {}
        }
    }



}