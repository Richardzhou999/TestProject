package com.uwei.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.util.ArrayList

/**
 * @Author Charlie
 * @Date 2022/7/19 11:59
 */
object ActivityManager {

    var activitys: MutableList<Activity> = ArrayList()

    /**
     * 跳转页面
     * @param context 对应上下文
     */
    fun intoActivity(context: Context,intoActivity: Activity){
        val intent = Intent(context,intoActivity::class.java);
        context.startActivity(intent)

    }

    fun intoActivity(activity: Activity,clz: Class<*>?){
        val intent = Intent(activity,clz);
        activity.startActivity(intent)
        removeActivity(activity)
    }

    fun intoActivity(context: Context,clz: Class<*>?,bundle: Bundle){
        val intent = Intent(context,clz);
        bundle?.let {
            intent.putExtras(bundle)
        }
        context.startActivity(intent)
    }

    fun intoActivity(activity: Activity,clz: Class<*>?,bundle: Bundle){
        val intent = Intent(activity,clz);
        bundle?.let {
            intent.putExtras(bundle)
        }
        activity.startActivity(intent)
        removeActivity(activity)
    }



    /**
     * 向List中添加一个活动
     * @param activity 活动
     */
    fun addActivity(activity: Activity) {
        activitys.add(activity)
    }

    /**
     * 从List中移除活动
     *
     * @param activity 活动
     */
    fun removeActivity(activity: Activity) {
        activity.finish()
        activitys.remove(activity)
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    fun finishAll() {
        for (activity in activitys) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

}