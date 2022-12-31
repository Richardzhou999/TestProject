package com.uwei.commom.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


/**
 * @Author Charlie
 * @Date 2022/8/12 14:28
 */
object NetworkUtils {

    /**
     * 判断当前是否是wifi
     * @param mContext
     * @return
     */
    fun isWifi(context: Context) : Boolean{

        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetwork
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {

            if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

            }
        }


        return false

    }

    /**
     * 判断网络连接
     * @param mContext
     * @return
     */
    fun isConnected(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        context?.let {
            if (Build.VERSION.SDK_INT < 23) {
                val mWiFiNetworkInfo = cm.activeNetworkInfo
                if (mWiFiNetworkInfo != null) {
                    if (mWiFiNetworkInfo.type == ConnectivityManager.TYPE_WIFI) { //WIFI
                        return true
                    } else if (mWiFiNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) { //移动数据
                        return true
                    }
                }
            } else {
                val network = cm.activeNetwork
                if (network != null) {
                    val nc = cm.getNetworkCapabilities(network)
                    if (nc != null) {
                        if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) { //WIFI
                            return true
                        } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) { //移动数据
                            return true
                        }
                    }
                }
            }
        }
        return false
    }



}