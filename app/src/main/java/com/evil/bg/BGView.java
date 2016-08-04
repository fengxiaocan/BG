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
    private Paint mPaintBlack;          //黑色画笔
    private Paint mPaintWhite;          //白色画笔
    private Paint mPaintRed;            //红色画笔
    private Paint mPaintHollowBlack;    //黑色空心画笔
    private Paint mPaintFont;           //黑色字体画笔
    private Paint mPaintRedFont;        //红色字体画笔
    private float mCx;                  //中心点x轴
    private float mCy;                  //中心点y轴
    private float mFont;                //字体大小
    private float mRadius;              //太极圆的半径
    private float mOutRadius;           //八卦最外圆的半径
    private float m8GRadius1;           //八卦外圆1的半径
    private float m8GRadius2;           //八卦外圆2的半径
    private float m8GRadius3;           //八卦外圆3的半径
    private float m8GRadius4;           //八卦外圆4的半径
    private float m8GRadius5;           //八卦外圆5的半径
    private float m8GRadius6;           //八卦外圆6的半径
    private float m8GRadius7;           //八卦外圆7的半径
    private float m8GRadius8;           //八卦外圆8的半径
    private float m8GRadius9;           //八卦外圆9的半径
    private float mTjDegress;           //太极圆旋转的角度
    private float mBgDegress;           //八卦旋转的角度
    private float mBg2Degress;          //八卦2旋转的角度
    private float mDoorDegress;         //门的旋转角度
    private float mTGDegress;           //天干的旋转角度
    private float mDZDegress;           //地支的旋转角度
    private float mJQDegress;           //二十四节气的旋转角度
    private float mDirectionDegress;    //方位的旋转角度
    private float mDirectionFixDegress; //方位的旋转固定角度
    private float mRectWidth;           //八卦仪矩形的宽的一半
    private float mRectHeight;          //八卦仪矩形的高的一半
    private float mInsideDistance;      //里爻到太极圆心的距离
    private float mMiddleDistance;      //中爻到太极圆心的距离
    private float mOuterDistance;       //外爻到太极圆心的距离
    private float mWhippletree;         //阴仪之间的距离的一半

    private final String[] fDirection = {"正南",
                                         "西南",
                                         "正西",
                                         "西北",
                                         "正北",
                                         "东北",
                                         "正东",
                                         "东南"};//方位
    private final String[] fTg        = {"甲",
                                         "乙",
                                         "丙",
                                         "丁",
                                         "戊",
                                         "己",
                                         "庚",
                                         "辛",
                                         "壬",
                                         "癸"};//天干
    private final String[] fDz        = {"子",
                                         "丑",
                                         "寅",
                                         "卯",
                                         "辰",
                                         "巳",
                                         "午",
                                         "未",
                                         "申",
                                         "酉",
                                         "戌",
                                         "亥"};//地支
    private final String[] fBg        = {"坤地",
                                         "震雷",
                                         "火离",
                                         "泽兑",
                                         "天乾",
                                         "风巽",
                                         "水坎",
                                         "山艮"};//方位
    private final int[][]  fTrigram   = {{2,
                                          1,
                                          1},
                                         {1,
                                          2,
                                          1},
                                         {2,
                                          2,
                                          2},
                                         {1,
                                          1,
                                          2},
                                         {1,
                                          1,
                                          1},
                                         {2,
                                          1,
                                          2},
                                         {2,
                                          2,
                                          1},
                                         {1,
                                          2,
                                          2}};//爻的变化
    private final String[] fBm        = {"开 门",
                                         "休 门",
                                         "生 门",
                                         "伤 门",
                                         "杜 门",
                                         "景 门",
                                         "惊 门",
                                         "死 门"};//八门遁甲
    private final String[] fJq        = {"立春",
                                         "雨水",
                                         "惊蛰",
                                         "春分",
                                         "清明",
                                         "谷雨",
                                         "立夏",
                                         "小满",
                                         "芒种",
                                         "夏至",
                                         "小暑",
                                         "大暑",
                                         "立秋",
                                         "处暑",
                                         "白露",
                                         "秋分",
                                         "寒露",
                                         "霜降",
                                         "立冬",
                                         "小雪",
                                         "大雪",
                                         "冬至",
                                         "小寒",
                                         "大寒"};//二十四节气
    private final String[] fXingShu   = {"星",
                                         "张",
                                         "翼",
                                         "轸",
                                         "奎",
                                         "娄",
                                         "胃",
                                         "昴",
                                         "毕",
                                         "觜",
                                         "参",
                                         "斗",
                                         "牛",
                                         "女",
                                         "虚",
                                         "危",
                                         "室",
                                         "壁",
                                         "角",
                                         "亢",
                                         "氐",
                                         "房",
                                         "心",
                                         "尾",
                                         "箕",
                                         "井",
                                         "鬼",
                                         "柳"};
    private       boolean  isRotate   = true;

    public BGView(Context context) {
        this(context, null);
        initPaint();
        initData();
    }

    public BGView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRadius = 100;    //太极的半径
        mTjDegress = 0;    //太极旋转的角度
        mBgDegress = 0;    //八卦旋转的角度
        mBg2Degress = 0;    //八卦2旋转的角度
        mDoorDegress = 0;    //门旋转的角度
        mTGDegress = 0;    //门旋转的角度
        mDZDegress = 0;    //门旋转的角度
        mJQDegress = 0;    //门旋转的角度
        mDirectionDegress = 0;//方位旋转的角度
        mDirectionFixDegress = 361;//方位固定的旋转的角度

        mRectWidth = mRadius / 2;                               //八卦仪矩形的宽的一半
        mRectHeight = mRectWidth / 5;                           //八卦仪矩形的高的一半
        mWhippletree = mRectHeight;                             //阴仪之间的距离的一半

        m8GRadius1 = mRadius + mRectWidth / 2 + mFont / 2;//八卦外的圆1半径
        m8GRadius2 = m8GRadius1 + mRectWidth / 2 + mFont / 2;//八卦外的圆2半径

        mInsideDistance = m8GRadius2 + mFont;                   //里爻到太极圆心的距离
        mMiddleDistance = mInsideDistance + mRectHeight + 3;    //中爻到太极圆心的距离
        mOuterDistance = mMiddleDistance + mRectHeight + 3;     //外爻到太极圆心的距离

        mInsideDistance = m8GRadius2 + mRectHeight;             //里爻到太极圆心的距离
        mMiddleDistance = mInsideDistance + mRectHeight + 3;    //中爻到太极圆心的距离
        mOuterDistance = mMiddleDistance + mRectHeight + 3;     //外爻到太极圆心的距离

        m8GRadius3 = m8GRadius2 + mRectWidth + mFont / 3;//八卦外的圆3半径
        m8GRadius4 = m8GRadius3 + mRectWidth + mFont / 3;//八卦外的圆4半径
        m8GRadius5 = m8GRadius4 + mRectWidth / 2 + mFont / 2;//八卦外的圆5半径
        m8GRadius6 = m8GRadius5 + mRectWidth / 2 + mFont / 2;//八卦外的圆6半径
        m8GRadius7 = m8GRadius6 + mRectWidth / 2 + mFont / 2;//八卦外的圆7半径
        m8GRadius8 = m8GRadius7 + mRectWidth / 2 + mFont / 2;//八卦外的圆8半径
        m8GRadius9 = m8GRadius8 + mRectWidth / 2 + mFont / 2;//八卦外的圆9半径
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
        //初始化红色画笔
        mPaintRed = new Paint();
        mPaintRed.setColor(Color.RED);
        mPaintRed.setAntiAlias(true);//去锯齿

        //初始化黑色空心画笔
        mPaintHollowBlack = new Paint();
        mPaintHollowBlack.setAntiAlias(true);//去锯齿
        mPaintHollowBlack.setStyle(Paint.Style.STROKE);//空心画笔
        //初始化字体画笔
        mFont = 30;        //字体大小
        mPaintFont = new Paint();
        mPaintFont.setAntiAlias(true);//去锯齿
        mPaintFont.setTextAlign(Paint.Align.CENTER);//设置字体居中
        mPaintFont.setTextSize(mFont);
        //初始化红色字体画笔
        mPaintRedFont = new Paint();
        mPaintRedFont.setColor(Color.RED);
        mPaintRedFont.setAntiAlias(true);//去锯齿
        mPaintRedFont.setTextAlign(Paint.Align.CENTER);//设置字体居中
        mPaintRedFont.setTextSize(15);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mOutRadius = getMeasuredWidth() / 2 - 3;
        //画太极
        drawTj(canvas);
        drawOutCircle(canvas);//最外面的外圆
        //方位
        drawDirection(canvas);
        drawCircle(canvas, m8GRadius1);//外圆1
        drawDoor(canvas);
        drawCircle(canvas, m8GRadius2);//外圆2
        draw8G(canvas, 0, true);//第一个八卦爻
        draw8G(canvas, m8GRadius3 - m8GRadius2, false);//第二个八卦爻
        drawCircle(canvas, m8GRadius3);//外圆3
        drawCircle(canvas, m8GRadius4);//外圆4
        drawCircle(canvas, m8GRadius5);//外圆5
        drawCircle(canvas, m8GRadius6);//外圆6
        drawCircle(canvas, m8GRadius7);//外圆7
        drawCircle(canvas, m8GRadius8);//外圆8
        drawCircle(canvas, m8GRadius9);//外圆9
        drawTianGan(canvas);//天干
        drawDiZhi(canvas);//地支
        draw24JQ(canvas);//二十四节气
        draw28XS(canvas);//二十八星宿
    }

    /**
     * 二十八星宿
     * @param canvas
     */
    private void draw28XS(Canvas canvas) {
        float fontY  = mCy + m8GRadius8 + mFont;//字体Y轴坐标
        float startY = mCy + m8GRadius8;//线开始的Y轴坐标
        float stopY  = mCy + m8GRadius9;//线结束的Y轴坐标
        drawText(canvas, mDirectionDegress, fXingShu, fontY, startY, stopY);//画字
    }

    /**
     * 二十四节气
     * @param canvas 画布
     */
    private void draw24JQ(Canvas canvas) {
        float fontY  = mCy + m8GRadius7 + mFont;//字体Y轴坐标
        float startY = mCy + m8GRadius7;//线开始的Y轴坐标
        float stopY  = mCy + m8GRadius8;//线结束的Y轴坐标
        drawText(canvas, -mJQDegress, fJq, fontY, startY, stopY);//画字
    }

    /**
     * 地支
     * @param canvas 画布
     */
    private void drawDiZhi(Canvas canvas) {
        float fontY  = mCy + m8GRadius6 + mFont;//字体Y轴坐标
        float startY = mCy + m8GRadius6;//线开始的Y轴坐标
        float stopY  = mCy + m8GRadius7;//线结束的Y轴坐标
        drawText(canvas, mDZDegress, fDz, fontY, startY, stopY);//画字
    }

    /**
     * 画字
     * @param canvas 画布
     * @param str 字的结合
     * @param fontY 字体Y轴坐标
     * @param startY 线开始的Y轴坐标
     * @param stopY 线结束的Y轴坐标
     */
    private void drawText(Canvas canvas, float degress, String[] str, float fontY, float startY, float stopY) {
        float rotateDegress = 360F / str.length / 2;
        canvas.rotate(degress, mCx, mCy);
        for (int i = 0; i < str.length; i++) {
            canvas.drawText(str[i], mCx, fontY, mPaintFont);
            canvas.rotate(rotateDegress, mCx, mCy);
            drawLine(canvas, startY, stopY);
            canvas.rotate(rotateDegress, mCx, mCy);
        }
        canvas.rotate(-degress, mCx, mCy);
    }

    /**
     * 天干
     * @param canvas 画布
     */
    private void drawTianGan(Canvas canvas) {
        float fontY  = mCy + m8GRadius5 + mFont;//字体Y轴坐标
        float startY = mCy + m8GRadius5;//线开始的Y轴坐标
        float stopY  = mCy + m8GRadius6;//线结束的Y轴坐标
        drawText(canvas, -mTGDegress, fTg, fontY, startY, stopY);//画字
    }

    /**
     * 画八卦的最外圆
     * @param canvas 画布
     */
    private void drawOutCircle(Canvas canvas) {
        float longY = mFont;
        float shot2 = mFont / 2;
        drawCircle(canvas, mOutRadius);
        for (int i = 0; i < 360; i++) {
            if (i % 10 == 0) {
                drawLine(canvas, 3, longY);
                canvas.drawText(i == 0
                                ? "360"
                                : i + "", mCx, longY, mPaintRedFont);
            } else {
                drawLine(canvas, 3, shot2);
            }
            canvas.rotate(1, mCx, mCy);
        }
    }

    /**
     * 八门遁甲
     * @param canvas 画布
     */
    private void drawDoor(Canvas canvas) {
        float fontY  = mCy + m8GRadius1 + mFont;//字体Y轴坐标
        float startY = mCy + m8GRadius1;//线的开始Y轴坐标
        float stopY  = mCy + m8GRadius2;//线的结束Y轴坐标
        drawText(canvas, -mDoorDegress, fBm, fontY, startY, stopY);//画字
    }

    /**
     * 画方位
     * @param canvas 画布
     */
    private void drawDirection(Canvas canvas) {
        canvas.rotate(mDirectionDegress, mCx, mCy);
        float fontY  = mCy + mRadius + mFont;//字体Y轴坐标
        float startY = mCy + mRadius;//线开始的Y轴坐标
        float stopY  = mCy + m8GRadius1;//线结束的Y轴坐标
        drawText(canvas, 0, fDirection, fontY, startY, stopY);//画字

        float startY1 = mCy + mRadius;
        float stopY1  = mCy + mOutRadius;
        for (int i = 0; i < 8; i++) {
            canvas.drawLine(mCx, startY1, mCx, stopY1, mPaintRed);
            canvas.rotate(45F, mCx, mCy);
        }
        canvas.rotate(-mDirectionDegress, mCx, mCy);
    }

    /**
     * 画线
     * @param canvas 画布
     */
    private void drawLine(Canvas canvas, float degress, float startY, float stopY) {
        canvas.rotate(-degress, mCx, mCy);
        canvas.drawLine(mCx, startY, mCx, stopY, mPaintHollowBlack);
        canvas.rotate(degress, mCx, mCy);
    }

    /**
     * 画线
     * @param canvas 画布
     * @param startY 线的开始Y轴坐标
     * @param stopY 线的结束Y轴坐标
     */
    private void drawLine(Canvas canvas, float startY, float stopY) {
        canvas.drawLine(mCx, startY, mCx, stopY, mPaintHollowBlack);
    }

    /**
     * 画八卦外圆
     * @param canvas 画布
     */
    private void drawCircle(Canvas canvas, float radius) {
        canvas.drawCircle(mCx, mCy, radius, mPaintHollowBlack);
    }

    /**
     * 画八卦阵
     * @param canvas 画布
     * @param distance 第二个八卦爻与第一个八卦爻的距离
     * @param isFrist 是否第一个八卦
     */
    private void draw8G(Canvas canvas, float distance, boolean isFrist) {
        float fontY  = mCy + m8GRadius4 + mFont - 3;//字体Y轴坐标
        float startY = mCy + m8GRadius2 + distance;//线开始的Y轴坐标
        float stopY1 = mCy + m8GRadius3;//线结束的Y轴坐标;
        float stopY2 = mCy + m8GRadius5;//线结束的Y轴坐标;
        if (isFrist) {
            //旋转画布
            canvas.rotate(mBgDegress, mCx, mCy);
        } else {
            //旋转画布
            canvas.rotate(-mBg2Degress, mCx, mCy);
        }

        //算出爻的坐标点
        float left    = mCx - mRectWidth;
        float left2   = mCx + mWhippletree;
        float top1    = mCy + mInsideDistance + distance;
        float top2    = mCy + mMiddleDistance + distance;
        float top3    = mCy + mOuterDistance + distance;
        float right   = mCx + mRectWidth;
        float right2  = mCx - mWhippletree;
        float bottom1 = top1 + mRectHeight;
        float bottom2 = top2 + mRectHeight;
        float bottom3 = top3 + mRectHeight;

        //画出爻
        for (int i = 0; i < fBg.length; i++) {
            for (int i1 = 0; i1 < fTrigram[i].length; i1++) {
                int trigram = fTrigram[i][i1];
                switch (i1) {
                    case 0:
                        //里爻
                        drawTrigram(canvas, trigram, left, top1, right, bottom1, left2, right2);
                        break;
                    case 1:
                        //中爻
                        drawTrigram(canvas, trigram, left, top2, right, bottom2, left2, right2);
                        break;
                    case 2:
                        //外爻
                        drawTrigram(canvas, trigram, left, top3, right, bottom3, left2, right2);
                        break;
                }
            }
            if (!isFrist) {
                //画字
                canvas.drawText(fBg[i], mCx, fontY, mPaintFont);
            }

            //画线
            drawLine(canvas,
                     22.5F,
                     startY,
                     isFrist
                     ? stopY1
                     : stopY2);
            //旋转画布
            canvas.rotate(45, mCx, mCy);
        }

        if (isFrist) {
            //反旋转画布
            canvas.rotate(-mBgDegress, mCx, mCy);
        } else {
            //反旋转画布
            canvas.rotate(mBg2Degress, mCx, mCy);
        }
    }

    /**
     *
     * @param canvas 画布
     * @param trigram 1:阳仪 2:阴仪
     * @param left 阴爻和阳爻最左边的左边
     * @param top 爻的顶点y轴坐标
     * @param right 爻的最右点
     * @param bottom 爻的最点左点
     * @param left2 阴爻的右横杠的左点
     * @param right2 阴爻的右横杠的右点
     */
    private void drawTrigram(Canvas canvas, int trigram, float left, float top, float right, float bottom, float left2, float right2) {
        if (trigram == 1) {
            drawOne(canvas, left, top, right, bottom);
        } else {
            drawTwo(canvas, left, top, right2, bottom, left2, right);
        }
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
        float sweepAngle = 180;
        canvas.drawArc(rectF, startAngleLeft, sweepAngle, true, mPaintBlack);


        //白色太极大半圆
        float startAngleRight = 270;
        canvas.drawArc(rectF, startAngleRight, sweepAngle, true, mPaintWhite);


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
     * 画八卦的阳仪
     * @param canvas 画布
     * @param left 左点
     * @param top 顶点
     * @param right 右点
     * @param bottom 下点
     */
    private void drawOne(Canvas canvas, float left, float top, float right, float bottom) {
        canvas.drawRect(left, top, right, bottom, mPaintBlack);
    }


    /**
     * 画八卦的阴仪
     * @param canvas 画布
     * @param left1 最左点
     * @param top 顶点
     * @param right1 左横杠的右点
     * @param bottom 下点
     * @param left2 右横杠的左点
     * @param right2 右横杠的最右点
     */
    private void drawTwo(Canvas canvas, float left1, float top, float right1, float bottom, float left2, float right2) {
        //左边的横杠
        canvas.drawRect(left1, top, right1, bottom, mPaintBlack);

        //右边的横杠
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
        if (isRotate) {
            //不停旋转
            postDelayed(mRunnable, 50);
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTjDegress += 1;
            mBgDegress += 5;
            mBg2Degress += 2;
            mDoorDegress += 3;
            mTGDegress += 3;
            mDZDegress += 4;
            mJQDegress += 4;
            //给指南针指定加速度并且有固定的方向指向
            mDirectionDegress = speed(mDirectionDegress, mDirectionFixDegress, 3F, 0.5F, true);
            //指南针
            mTjDegress = mTjDegress % 360;
            mBgDegress = mBgDegress % 360;
            mBg2Degress = mBg2Degress % 360;
            mDoorDegress = mDoorDegress % 360;
            mTGDegress = mTGDegress % 360;
            mDZDegress = mDZDegress % 360;
            mJQDegress = mJQDegress % 360;
            invalidate();//重新绘制
            //不停循环
            rotateView();
        }
    };

    /**
     * 加速变速角度
     * @param degress 要改变的角度
     * @param fixDegress 固定的参照角度
     * @param maxSpeed 最大的加速度
     * @param minSpeed 最小的加速度
     * @param isFix 达到固定的角度是否停止加速
     * @return 返回改变后的角度
     */
    private float speed(float degress, float fixDegress, float maxSpeed, float minSpeed, boolean isFix) {
        if (degress > fixDegress) {
            if (degress - fixDegress > maxSpeed * 2) {
                degress -= maxSpeed;
            } else if (isFix && degress - fixDegress <= minSpeed) {
                degress = fixDegress;
            } else {
                degress -= minSpeed;
            }
        } else {
            if (fixDegress - degress > maxSpeed * 2) {
                degress += maxSpeed;
            } else if (isFix && fixDegress - degress <= minSpeed) {
                degress = fixDegress;
            } else {
                degress += minSpeed;
            }
        }
        degress = degress % 360;
        return degress;
    }

    /**
     * 从window中移除时
     */
    @Override
    protected void onDetachedFromWindow() {
        //停止旋转
        removeCallbacks(mRunnable);
        super.onDetachedFromWindow();
    }

    /**
     * 开始旋转
     */
    public void startRotate() {
        isRotate = true;
        rotateView();
    }

    /**
     * 停止旋转
     */
    public void stopRotate() {
        isRotate = false;
    }

    /**
     * 获取是否旋转的状态
     * @return 是否旋转
     */
    public boolean getRotateState() {
        return isRotate;
    }

    /**
     * 设置指南针的方向
     * @param fix
     */
    public void setDirectionFix(float fix) {
        mDirectionFixDegress = fix;
    }
}
