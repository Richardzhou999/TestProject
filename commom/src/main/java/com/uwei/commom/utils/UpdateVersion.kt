package com.uwei.commom.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.lifeOnMain
import com.uwei.commom.R
import com.uwei.commom.utils.AppInfoUtils.installApk
import com.uwei.commom.widget.CustomProgressBar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * @Author Charlie
 * @Date 2022/8/19 10:20
 * 实现App的版本更新
 */
class UpdateVersion(

        private var mContext: Context,
        /**
         * 版本号
         */
        private var mVersionCode: Int,

        /**
         * 版本名称
         */
        private var mVersionName: String,

        /**
         * 更新提示内容
         */
        private var mUpdateContent: String,

        /**
         * 更新的APK对应的网络地址
         */
        private var mUpdateAPKUrl: String,

        /**
         * 更新的类型 1：正常更新，2：强制更新
         */
        private var mUpdateStatus: Int = 1,

        /**
         * 更新的时候是否在前台开启进度条
         */
        private var isShowUpdate: Boolean = false,
        /**
         * 进度返回
         */
        private var isCallBack: Boolean = false,
        /**
         * 进度返回
         */
        private var progressCallBack: OnProgressCallBack?,

) {

    /**
     * APK下载之后保存的地址
     */
    var mSavePath: String? = null
    /**
     * 保存的文件名
     */
    var mSaveFileName: String? = null
    /**
     * 当前下载的进度
     */
    private var mDownloadProgress: Int = 0
    /**
     * 版本号
     */
    private var mUpdateVersion: TextView? = null
    /**
     * 更新内容文本
     */
    private var mUpdateText: TextView? = null
    /**
     * 取消
     */
    private var mUpdateCancel: TextView? = null
    /**
     * 确认
     */
    private var mUpdateSure: TextView? = null
    /**
     *
     */
    private var mProgressBar: CustomProgressBar? = null
    /**
     *
     */
    private var mProgressBarText: TextView? = null
    /**
     * 更新弹出的确认更新确认框
     */
    private var mDialog: AlertDialog? = null
    /**
     * 通知上的logo
     */
    private var mLogo: Int? = null
    /**
     *
     */
    private var mOwner: LifecycleOwner? = null

    interface OnProgressCallBack{
        fun getProgress(progress: Int)
    }

    fun createUpdateDialog(){
        initDialogView()
    }

    fun createUpdateDialog(updateView: View,version: TextView?,content: TextView?,progressBar: CustomProgressBar?,
                           progressText: TextView?, cancel: TextView?, sure: TextView?){
        mUpdateVersion = version
        mUpdateText = content
        mUpdateCancel = cancel
        mUpdateSure = sure
        mProgressBar = progressBar
        mProgressBarText = progressText

        createDialog(updateView)
    }

    //临时
    private var mLayout: FrameLayout? = null
    private var mIesId: Int? = null
    private var mVersionText: TextView? = null
    fun setLayout(layout: FrameLayout,iesId: Int,textView: TextView){
        mLayout = layout
        mIesId = iesId
        mVersionText = textView
    }


    fun setLogo(logo: Int){
        mLogo = logo
    }

    fun setOwner(owner: LifecycleOwner){
        mOwner = owner
    }

    fun updateVersion(){
        val versionName = "版本为：$mVersionName"
        mUpdateVersion?.text = versionName
        if (!TextUtils.isEmpty(mUpdateContent)) {
            mUpdateText?.text = mUpdateContent
        } else {
            mUpdateText?.text = "暂无更新"
        }
        // 设置下载的安装路径
        mSavePath = mContext!!.getExternalFilesDir(null)!!.absolutePath + File.separator + "apk"
        mSaveFileName = mSavePath + File.separator + mVersionCode.toString() + ".apk"
        // 取消更新
        mUpdateCancel?.setOnClickListener { v ->
            if (mDialog != null && mDialog?.isShowing == true)
                mDialog?.dismiss()
        }
        // 确认更新
        mUpdateSure?.setOnClickListener { v ->
            val file = File(mSaveFileName)
            if (file.exists()) {
                // apk文件已经存在，直接安装
                installApk(mContext, mSaveFileName!!)
            } else {
                // 后台开启下载功能，下载完毕后自动更新
                if (isShowUpdate) {
                    mLayout?.background = mIesId?.let { mContext.getDrawable(it) }
                    mProgressBar?.visibility = View.VISIBLE
                    mProgressBarText?.visibility = View.VISIBLE
                    mUpdateCancel?.visibility = View.GONE
                    mUpdateSure?.visibility = View.GONE
                    mUpdateVersion?.visibility = View.GONE
                    mVersionText?.visibility = View.GONE
                    startDownloadApk()
                } else {
                    if (mDialog != null && mDialog?.isShowing == true) {
                        mDialog?.dismiss()
                    }
                    if(!NotificationManagerCompat.from(mContext).areNotificationsEnabled()){
                        val intent = Intent()
                        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        intent.putExtra("app_package", mContext.packageName);
                        intent.putExtra("app_uid", mContext.applicationInfo.uid)
                        // for Android 8 and above
                        intent.putExtra("android.provider.extra.APP_PACKAGE", mContext.packageName);
                        mContext.startActivity(intent)
                    }else{
                        startDownloadApk()
                    }
                }

            }
        }
        mDialog?.show()
    }


    private fun createDialog(updateView: View) {
        val builder = AlertDialog.Builder(mContext)
        builder.setView(updateView)
        mDialog = builder.create()
        mDialog?.window?.decorView?.setBackgroundColor(ContextCompat.getColor(mContext,android.R.color.transparent))

        // 判断是否为强制更新
        if (mUpdateStatus == 2) {
            mDialog?.setCanceledOnTouchOutside(false)
            mUpdateCancel?.visibility = View.GONE
            mDialog?.setOnKeyListener(keyListener)
            mDialog?.setCancelable(false)
        } else {
            mDialog?.setCanceledOnTouchOutside(true)
        }
    }

    private fun initDialogView() {
        val updateView: View = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_version, null)
        mUpdateVersion = updateView.findViewById(R.id.new_version_value)
        mUpdateText = updateView.findViewById(R.id.update_content)
        mUpdateCancel = updateView.findViewById(R.id.cancel)
        mUpdateSure = updateView.findViewById(R.id.sure)
        createDialog(updateView)
    }

    /**
     * 禁用返回键
     */
    private val keyListener = DialogInterface.OnKeyListener { dialog: DialogInterface?, keyCode: Int,
                                                              event: KeyEvent ->
        keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0
    }

    private fun startDownloadApk(){
        val okHttpClient = OkHttpClient();
        val request = Request.Builder().url(mUpdateAPKUrl).build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                ToastUtil.showText(mContext,"下载失败")
            }

            @SuppressLint("CheckResult")
            override fun onResponse(call: Call, response: Response) {

                var input: InputStream? = null
                var fos: FileOutputStream? = null
                var buf = ByteArray(2048)
                var int = 0
                var len = 0
                //储存下载文件的目录
                var dir = File(mSavePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                var file = File(mSaveFileName);
                try {
                    input = response.body()!!.byteStream()
                    val total = response.body()!!.contentLength()
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (input.read(buf).also { len = it } != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        if (isCallBack) {
                            //下载中更新进度条
                            mOwner?.let {
                                Observable.just(progress).subscribeOn(Schedulers.io())
                                        .lifeOnMain(it)
                                        .subscribe { integer: Int ->
                                            progressCallBack?.getProgress(integer)
                                        }
                            }
                        } else {

                            if(isShowUpdate){
                                mOwner?.let {
                                    Observable.just(progress).subscribeOn(Schedulers.io())
                                            .lifeOnMain(it)
                                            .subscribe { integer: Int? ->
                                                if (integer != null) {
                                                    mProgressBar?.setTexColor(ContextCompat.getColor(mContext,R.color.white),
                                                            ContextCompat.getDrawable(mContext,R.mipmap.icon_progress))
                                                    mProgressBar?.progress = integer
                                                }
                                            }
                                }
                            }else{
                                mOwner?.let {
                                    Observable.just(progress).subscribeOn(Schedulers.io())
                                            .lifeOnMain(it)
                                            .subscribe { integer: Int? -> updateNotification(progress) }
                                }
                            }
                        }
                    }
                    fos?.flush();
                    //下载完成
                    mOwner?.let {
                        Observable.just(100).subscribeOn(Schedulers.io())
                                .lifeOnMain(it)
                                .subscribe { integer: Int? ->
                                    installApk(mContext,mSaveFileName!!)
                                }
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                } finally {
                    try {
                        input?.close()
                        fos?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    /**
     * 通知栏更新
     */
    private fun updateNotification(progress: Int) {
        if (NotificationManagerCompat.from(mContext).areNotificationsEnabled()) {
            if (progress >= mDownloadProgress) {
                mDownloadProgress = progress
                val manager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationChannel = NotificationChannel("1", "update", NotificationManager.IMPORTANCE_HIGH)
                    notificationChannel.setSound(null, null)
                    notificationChannel.enableLights(false)
                    notificationChannel.lightColor = Color.RED
                    notificationChannel.setShowBadge(false)
                    notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    manager.createNotificationChannel(notificationChannel)
                }
                // 创建自定义的样式布局
                val remoteViews = RemoteViews(mContext.packageName, R.layout.update_version)
                // 在这里可以设置RemoteView的初始布局
                val builder: NotificationCompat.Builder = NotificationCompat.Builder(mContext, "1")

                // 不可以自动取消
                builder.setAutoCancel(false)
                // 必须要设置，否则在android 10手机上面会闪退
                mLogo?.let { builder.setSmallIcon(it) }
                // 设置通知的优先级
                builder.priority = NotificationCompat.PRIORITY_MAX
                remoteViews.setImageViewResource(R.id.download_progress_img, mLogo!!)
                remoteViews.setTextViewText(R.id.download_progress_name, mVersionName)
                CHLog.e(mDownloadProgress)
                if(mDownloadProgress >= 98){
                    remoteViews.setViewVisibility(R.id.txt_download_end,View.VISIBLE)
                    remoteViews.setViewVisibility(R.id.download_progressbar,View.GONE)
                    remoteViews.setViewVisibility(R.id.download_progress_text,View.GONE)
                }else{
                    remoteViews.setViewVisibility(R.id.txt_download_end,View.GONE)
                    remoteViews.setProgressBar(R.id.download_progressbar, 100, mDownloadProgress, false)
                    remoteViews.setTextViewText(R.id.download_progress_text, "$mDownloadProgress%")
                }
                builder.setCustomContentView(remoteViews)
                manager.notify(1, builder.build())
            }
        }
    }

}