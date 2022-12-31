package com.uwei.commom.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.uwei.commom.R


/**
 * @Author Charlie
 * @Date 2022/8/25 16:52
 * 动态移动进度条
 */
class CustomProgressBar : ProgressBar {
    private var mContext: Context? = null
    private var text: String? = null
    private var mPaint: Paint? = null
    private var mLogo: Bitmap? = null
    private var girlBitWidth: Int = 0
    private var girlBitHeight:Int = 0
    private var girlSrcRect: Rect? = null
    private var girlDesRect: Rect? = null

    constructor(context: Context?) : super(context) {
        mContext = context
        initText()

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        initText()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        initText()
    }

    @Synchronized
    override fun setProgress(progress: Int) {
        val i = progress * 100 / this.max
        text = "$i%"
        super.setProgress(progress)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        girlSrcRect = Rect(0, 0, 20, 50)

    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect = Rect()
        mPaint?.getTextBounds(text, 0, text!!.length, rect)
        val x: Int = (this.progress * 100 / width) +
                ((this.progress + text?.length!!) * 6) -
                (rect.centerX() * 3 + 10)
        val y: Int = height / 2 - rect.centerY()
        mPaint?.let {
            text?.let {
                it1 -> canvas.drawText(it1, x.toFloat(), y.toFloat(), it)
            }
        }

        girlDesRect = Rect(0, 0, rect.centerX() / 2,rect.centerX() / 2)
        mLogo?.let {
            canvas.drawBitmap(it, girlSrcRect, girlDesRect!!, mPaint);
        }
    }

    private fun initText() {
        mPaint = Paint()
        mPaint?.textSize = 23F

    }

    fun setTexColor(textColor: Int?,logo: Drawable?){
        textColor?.let {
            mPaint?.color = it
        }
        logo?.let {
            mLogo = (it as BitmapDrawable).bitmap
        }
        var drawable = mContext?.let { ContextCompat.getDrawable(it, R.mipmap.icon_progress) }
        mLogo = drawable?.let { drawableToBitamp(it) }
        girlBitWidth = mLogo?.width!!;
        girlBitHeight = mLogo?.height!!;
    }

    private fun drawableToBitamp(drawable: Drawable): Bitmap? {
        //声明将要创建的bitmap
        var bitmap: Bitmap? = null
        //获取图片宽度
        val width = drawable.intrinsicWidth
        //获取图片高度
        val height = drawable.intrinsicHeight
        //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        //创建一个空的Bitmap
        bitmap = Bitmap.createBitmap(width, height, config)
        //在bitmap上创建一个画布
        val canvas = Canvas(bitmap)
        //设置画布的范围
        drawable.setBounds(0, 0, width, height)
        //将drawable绘制在canvas上
        drawable.draw(canvas)
        return bitmap
    }


}