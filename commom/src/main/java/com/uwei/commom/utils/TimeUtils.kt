package com.uwei.commom.utils

import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * @Author Charlie
 * @Date 2022/8/2 17:30
 */
object TimeUtils {

    @JvmStatic
    fun timeComparison(expirationTime: Long): Boolean {
        //获得当前时间戳
        val timeStamp = System.currentTimeMillis()
        //格式
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //转换为String类型
        val endDate = formatter.format(expirationTime) //结束的时间戳
        val startDate = formatter.format(timeStamp) //开始的时间戳
        try {
            val d1 = formatter.parse(endDate) //后的时间
            val d2 = formatter.parse(startDate) //前的时间
            val diff = d1.time - d2.time //两时间差，精确到毫秒
            val day = diff / (1000 * 60 * 60 * 24) //以天数为单位取整
            val hour = diff / (60 * 60 * 1000) - day * 24 //以小时为单位取整
            val min = diff / (60 * 1000) - day * 24 * 60 - hour * 60 //以分钟为单位取整
            val second = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60 //秒
            if (min <= 2) {
                return false
            }
            Log.e("tag", "day =$day")
            Log.e("tag", "hour =$hour")
            Log.e("tag", "min =$min")
            Log.e("tag", "second =$second")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return true
    }
}