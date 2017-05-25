package juice.circleprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author chengyong
 * @time 2017/3/27 17:44
 * @des  圆形进度条
 */
public class CircleProgressView extends LinearLayout {

    private ImageView mIvIcon;
    private TextView mTvNote;
    private long mMax = 100;
    private long mProgress;

    /**
     * 设置进度的当前值
     */
    public void setProgress(long progress) {
        mProgress = progress;
        //重绘
        invalidate();
    }

    /**
     * 设置进度的最大值
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置图标
     */
    public void setIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 设置文本内容
     */
    public void setNote(String content) {
        mTvNote.setText(content);
    }

    public CircleProgressView(Context context) {
        this(context, null);
        initOriginView(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initOriginView(context);

    }

    private void initOriginView(Context context) {
        //挂载对应的xml
        View view = View.inflate(context, R.layout.inflate_progressview, this);
        mIvIcon = (ImageView) view.findViewById(R.id.progressView_iv_icon);
        mTvNote = (TextView) view.findViewById(R.id.progressView_tv_note);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//绘制背景
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);//绘制图标+文本
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);// 绘制边框的形式
            paint.setStrokeWidth(3);
            paint.setColor(Color.BLUE);
            paint.setAntiAlias(true);//消除锯齿
            float left = mIvIcon.getLeft();
            float top = mIvIcon.getTop();
            float right = mIvIcon.getRight();
            float bottom = mIvIcon.getBottom();
            RectF oval = new RectF(left, top, right, bottom);//限制弧形绘制范围
            float startAngle = -90;//开始角度
            float sweepAngle = mProgress * 1.0f / mMax * 360;//扫过的角度==>动态计算
            boolean useCenter = false;
            canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }
}
