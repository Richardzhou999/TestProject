package com.uwei.commom.utils

import android.content.Context
import android.os.Looper
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess

/**
 * @Author Charlie
 * @Date 2022/8/6 15:56
 * 错误日志收集
 */
class CrashHandler : Thread.UncaughtExceptionHandler {

    private var mContext: Context? = null
    fun init(ctx: Context?) {
        mContext = ctx
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(arg0: Thread, arg1: Throwable) {
        val logDir: String
        if (mContext!!.getExternalFilesDir(null) != null) {
            logDir = mContext!!.getExternalFilesDir(null)!!.absolutePath + File.separator + "UWLog"
            val file = File(logDir)
            val mkSuccess: Boolean
            if (!file.isDirectory) {
                mkSuccess = file.mkdirs()
                if (!mkSuccess) {
                    file.mkdirs()
                }
            }
            try {
                val dir = logDir + File.separator + "error.log"
                val fw = FileWriter(dir, true)
                if (fw != null) {
                    fw.write("${Date()}".trimIndent())
                    val stackTrace = arg1.stackTrace
                    fw.write("${arg1.message}".trimIndent())
                    for (i in stackTrace.indices) {
                        fw.write("file:${stackTrace[i].fileName} " +
                                "class:${stackTrace[i].className} " +
                                "method:${stackTrace[i].methodName} " +
                                "line:${stackTrace[i].lineNumber}")
                    }
                    fw.write("\n")
                    fw.close()
                }
            } catch (e: IOException) {
                Log.e("crash handler", "load file failed...", e.cause)
            }
        }
        arg1.printStackTrace()
        object : Thread() {
            override fun run() {
                Looper.prepare()
                ToastUtil.showText(mContext, "遇到异常了")
                Looper.loop()
            }
        }.start()
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        exitProcess(0)
    }

    companion object {
        @JvmStatic
        var instance: CrashHandler? = null
            get() {
                synchronized(CrashHandler::class.java) {
                    if (field == null) {
                        field = CrashHandler()
                    }
                }
                return field
            }
            private set
    }
}