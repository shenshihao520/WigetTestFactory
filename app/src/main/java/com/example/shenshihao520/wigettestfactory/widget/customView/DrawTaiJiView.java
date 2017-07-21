package com.example.shenshihao520.wigettestfactory.widget.customView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * 自定义View  画太极
 * Created by shenshihao520 on 2017/7/19.
 */

public class DrawTaiJiView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Context mContext;

    //创建两个绘画路径
    private Path path0 = new Path();
    private Path path1 = new Path();

    public DrawTaiJiView(Context context) {

        super(context);
        mContext = context;
    }

    public DrawTaiJiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

    }

    public DrawTaiJiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

    }

    private void init() {
        initPaint();
    }

    void initPaint() {
        mPaint = new Paint();      //创建画笔对象
        mPaint.setColor(Color.BLACK);   //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(5f);     //设置画笔宽度为10px
        mPaint.setAntiAlias(true);     //设置抗锯齿
        mPaint.setAlpha(255);        //设置画笔透明度
    }

    /**
     * 这个方法可以获得宽高
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth  = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        //这里可以应用三种测量模式
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 400;
        int mHeight = 400;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }


    //还需优化过度绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        //移动到屏幕中间
        canvas.translate((mWidth- getPaddingTop() * 2 ) / 2, mHeight / 2);
        path1.rewind();
        path0.rewind();

        mPaint.setStyle(Paint.Style.STROKE);
        path0.addCircle(getPaddingLeft(), 0, (mWidth- getPaddingTop() * 2 )/2, Path.Direction.CW);
        canvas.drawPath(path0, mPaint);

        path0.rewind();     //去除原路径
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        path0.addCircle(getPaddingLeft(), 0, (mWidth- getPaddingTop() * 2 )/2, Path.Direction.CW);
        canvas.drawPath(path0, mPaint);

        mPaint.setColor(Color.BLACK);
        path1.addCircle(getPaddingLeft(), 0, (mWidth- getPaddingTop() * 2 )/2, Path.Direction.CW);

        path0.rewind();
        path0.addRect(getPaddingLeft(), -(mWidth- getPaddingTop() * 2 )/2, (mWidth- getPaddingRight() * 2 )/2 + getPaddingRight(), (mWidth- getPaddingTop() * 2 )/2, Path.Direction.CW);
        path1.op(path0, Path.Op.DIFFERENCE);


        path0.rewind();
        path0.addCircle(getPaddingLeft(), -(mWidth- getPaddingRight() * 2 )/4, (mWidth- getPaddingTop() * 2 )/4, Path.Direction.CW);
        path1.op(path0, Path.Op.DIFFERENCE);

        path0.rewind();
        path0.addCircle(getPaddingLeft(), (mWidth- getPaddingRight() * 2 )/4, (mWidth- getPaddingTop() * 2 )/4, Path.Direction.CW);
        path1.op(path0, Path.Op.UNION);


        path0.rewind();
        path0.addCircle(getPaddingLeft(), (mWidth- getPaddingRight() * 2 )/4, (mWidth- getPaddingTop() * 2 )/8, Path.Direction.CW);
        path1.op(path0, Path.Op.DIFFERENCE);

        path0.rewind();
        path0.addCircle(getPaddingLeft(), -(mWidth- getPaddingRight() * 2 )/4, (mWidth- getPaddingTop() * 2 )/8, Path.Direction.CW);
        mPaint.setColor(Color.BLACK);
        path1.op(path0, Path.Op.UNION);

        canvas.drawPath(path1, mPaint);

        rotate();

    }


    void drawOld(Canvas canvas) {
        //画黄方块
        mPaint.setColor(Color.BLACK);
       /* Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
        Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；
        这东西和文字顺序有关系
        */
        mPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式为填充

        path0.addCircle(0, 0, 300, Path.Direction.CW);
        canvas.drawPath(path0, mPaint);


        //中间扣个圆  白鱼
        mPaint.setColor(Color.WHITE);
        path0.rewind();     //去除原路径
        path0.addCircle(0, 0, 300, Path.Direction.CW);
        canvas.drawPath(path0, mPaint);
        //黑鱼的背景
        mPaint.setColor(Color.BLACK);
        path1.addCircle(0, 0, 300, Path.Direction.CW);

        path0.rewind();
        path0.addRect(0, -300, 300, 300, Path.Direction.CW);
        path1.op(path0, Path.Op.DIFFERENCE);

        path0.rewind();
        path0.addCircle(0, -150, 150f, Path.Direction.CW);
        path1.op(path0, Path.Op.UNION);


        path0.rewind();
        path0.addCircle(0, 150, 150f, Path.Direction.CW);
        path1.op(path0, Path.Op.DIFFERENCE);

        path0.rewind();
        path0.addCircle(0, -150, 75f, Path.Direction.CW);
        path1.op(path0, Path.Op.DIFFERENCE);

        path0.rewind();
        path0.addCircle(0, 150, 75f, Path.Direction.CW);
        mPaint.setColor(Color.BLACK);
        path1.op(path0, Path.Op.UNION);

        canvas.drawPath(path1, mPaint);//这一段注意，之后要删除
    }
    public void rotate()
    {
        AnimatorSet set2 = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.anim.sun_1_rotation);
        set2.setTarget(this);
        set2.start();
    }
}
