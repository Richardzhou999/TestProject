package com.uwei.commom.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File


/**
 * @Author Charlie
 * @Date 2022/8/11 10:28
 */
object AppInfoUtils {



    /**
     * 检测是否安装某app
     * @param context
     * @param pkgName
     * @return
     */
    fun isAppInstalled(context: Context, pkgName: String?): Boolean {
        var packageInfo: PackageInfo?
        try {
            packageInfo = context.packageManager.getPackageInfo(pkgName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            packageInfo = null
            e.printStackTrace()
        }
        return packageInfo != null
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    fun isWechatInstalled(context: Context) : Boolean{
       return isAppInstalled(context, "com.tencent.mm")
    }

    /**
     * 判断 用户是否安装支付宝客户端
     */
    fun isAlipayInstalled(context: Context) : Boolean{
        return isAppInstalled(context, "com.eg.android.AlipayGphone")
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    fun isQQInstalled(context: Context) : Boolean{
        return isAppInstalled(context, "com.tencent.qqlite") ||
                isAppInstalled(context, "com.tencent.mobileqq")
    }

    /**
     * 安装apk
     */
    fun installApk(mContext: Context,filePath: String){
        val file = File(filePath)
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            val authority: String = mContext.packageName.toString() + ".fileProvider"
            val apkUri: Uri = FileProvider.getUriForFile(mContext, authority, file)
            val install = Intent(Intent.ACTION_VIEW)
            install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive")
            mContext.startActivity(install)
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive")
            mContext.startActivity(intent)
        }
    }
}