package com.evil.bg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/*
 *  @项目名：  BG 
 *  @包名：    com.evil.bg
 *  @文件名:   BGView
 *  @创建者:   冯小灿
 *  @创建时间:  2016/7/24 16:37
 *  @描述：    八卦View
 */
public class BGView
        extends View
{
    private static final String TAG = "BGView";
    private Paint mPaintBlack;          //黑色画笔
    private Paint mPaintWhite;          //白色画笔
    private float mCx;                  //中心点x轴
    private float mCy;                  //中心点y轴
    private float mRadius;              //太极圆的半径
    private float mTjDegress;             //太极圆旋转的角度
    private float mBgDegress;             //八卦旋转的角度
    private float mRectWidth;           //八卦矩形的宽的一半
    private float mRectHeight;          //八卦矩形的高的一半
    private float mInsideDistance;      //里八卦矩形到太极圆的距离
    private float mMiddleDistance;      //中八卦矩形到太极圆的距离
    private float mOuterDistance;       //外八卦矩形到太极圆的距离
    private float mWhippletree;         //外八卦双矩形之间的距离的一半

    public BGView(Context context) {
        this(context, null);
        initPaint();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRadius = 200;    //太极的半径
        mTjDegress = 0;    //太极旋转的角度
        mBgDegress = 0;    //八卦旋转的角度
        mRectWidth = mRadius / 3;   //八卦矩形的宽的一半
        mRectHeight = mRectWidth / 5;   //八卦矩形的高的一半
        mWhippletree = mRectHeight;   //外八卦双矩形之间的距离的一半
        mInsideDistance = mRadius / 10;   //里八卦矩形到太极圆的距离
        mMiddleDistance = mInsideDistance * 2;   //中八卦矩形到太极圆的距离
        mOuterDistance = mInsideDistance * 3;   //外八卦矩形到太极圆的距离
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //初始化黑色画笔
        mPaintBlack = new Paint();
        mPaintBlack.setAntiAlias(true);//去锯齿
        //初始化白色画笔
        mPaintWhite = new Paint();
        mPaintWhite.setColor(Color.WHITE);
        mPaintWhite.setAntiAlias(true);//去锯齿
    }

    public BGView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画太极
        drawTj(canvas);
        draw8G(canvas);
    }

    /**
     * 画八卦阵
     * @param canvas
     */
    private void draw8G(Canvas canvas) {
        //保存状态
        canvas.save();
        //旋转画布
        canvas.rotate(mBgDegress, mCx, mCy);
        //坤地
        drawTwo(canvas, mInsideDistance);
        drawTwo(canvas, mMiddleDistance);
        drawTwo(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //震雷
        drawOne(canvas, mInsideDistance);
        drawTwo(canvas, mMiddleDistance);
        drawTwo(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //火离
        drawOne(canvas, mInsideDistance);
        drawTwo(canvas, mMiddleDistance);
        drawOne(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //泽兑
        drawOne(canvas, mInsideDistance);
        drawOne(canvas, mMiddleDistance);
        drawTwo(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //天乾
        drawOne(canvas, mInsideDistance);
        drawOne(canvas, mMiddleDistance);
        drawOne(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //风巽
        drawTwo(canvas, mInsideDistance);
        drawOne(canvas, mMiddleDistance);
        drawOne(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //水坎
        drawTwo(canvas, mInsideDistance);
        drawOne(canvas, mMiddleDistance);
        drawTwo(canvas, mOuterDistance);

        //旋转画布
        canvas.rotate(45 + mBgDegress, mCx, mCy);
        //山艮
        drawTwo(canvas, mInsideDistance);
        drawTwo(canvas, mMiddleDistance);
        drawOne(canvas, mOuterDistance);
        //还原状态
        canvas.restore();
    }

    /**
     * 绘制八卦
     */
    private void drawTj(Canvas canvas) {
        //圆心x轴
        mCx = getMeasuredWidth() / 2;
        //圆心y轴
        mCy = getMeasuredHeight() / 2;

        //旋转画布
        canvas.rotate(-mTjDegress, mCx, mCy);

        //黑色太极
        //画一个中心的半圆
        RectF rectF = new RectF(mCx - mRadius, mCy - mRadius, mCx + mRadius, mCy + mRadius);
        //起始的角度
        float startAngleLeft = 90;
        //扫过的角度
        float   sweepAngle = 180;
        boolean useCenter  = true;
        canvas.drawArc(rectF, startAngleLeft, sweepAngle, useCenter, mPaintBlack);


        //白色太极大半圆
        float startAngleRight = 270;
        canvas.drawArc(rectF, startAngleRight, sweepAngle, useCenter, mPaintWhite);


        //白色圆Y轴的位置----太阳
        float topY         = mCy - mRadius / 2;
        float middleRadius = mRadius / 2;
        canvas.drawCircle(mCx, topY, middleRadius, mPaintWhite);


        //画黑色的中太极半圆----太阴
        //太极圆Y轴的位置
        float belowY = mCy + mRadius / 2;
        canvas.drawCircle(mCx, belowY, middleRadius, mPaintBlack);
        //--少阴
        //画黑色的小太极圆
        //太极圆Y轴的位置
        float minRadius = middleRadius / 2;
        canvas.drawCircle(mCx, topY, minRadius, mPaintBlack);
        //--少阳
        //画白色的小太极圆
        canvas.drawCircle(mCx, belowY, minRadius, mPaintWhite);

        //旋转回来
        canvas.rotate(mTjDegress, mCx, mCy);
    }

    /**
     * 画上单杠
     */
    private void drawOne(Canvas canvas, float height) {
        float left   = mCx - mRectWidth;
        float top    = mCy + mRadius + height;
        float right  = mCx + mRectWidth;
        float bottom = top + mRectHeight;
        canvas.drawRect(left, top, right, bottom, mPaintBlack);
    }


    /**
     * 画八卦的双横杠
     * @param canvas
     */
    private void drawTwo(Canvas canvas, float height) {
        //左边的横杠
        float left1  = mCx - mRectWidth;
        float top    = mCy + mRadius + height;
        float right1 = mCx - mWhippletree;
        float bottom = top + mRectHeight;
        canvas.drawRect(left1, top, right1, bottom, mPaintBlack);

        //右边的横杠
        float left2  = mCx + mWhippletree;
        float right2 = mCx + mRectWidth;
        canvas.drawRect(left2, top, right2, bottom, mPaintBlack);
    }

    /**
     * 添加上window上时
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //旋转八卦
        rotateView();
    }

    /**
     * 旋转八卦鱼
     */
    private void rotateView() {
        //不停旋转
        postDelayed(mRunnable, 50);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTjDegress += 1;
            mBgDegress += 1;
            mTjDegress = mTjDegress % 360;
            mBgDegress = mBgDegress % 360;
            invalidate();//重新绘制
            //不停循环
            rotateView();
        }
    };

    /**
     * 从window中移除时
     */
    @Override
    protected void onDetachedFromWindow() {
        //停止旋转
        removeCallbacks(mRunnable);
        super.onDetachedFromWindow();
    }
}
