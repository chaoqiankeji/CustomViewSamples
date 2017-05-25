package juice.progressbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 带进度条的矩形按钮
 */
public class ProgressBtn extends Button {
    private long mMax = 100;
    private long mProgress;
    private boolean isProgressEnable = true;
    private Drawable mDrawable;

    /**
     * 设置进度的最大值
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置进度的当前值
     */
    public void setProgress(long progress) {
        mProgress = progress;
        //重新绘制drawable
        invalidate();
    }

    public ProgressBtn(Context context) {
        super(context);
    }

    public ProgressBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isProgressEnable) {
            if (mDrawable == null) {
                mDrawable = new ColorDrawable(Color.RED);
            }
            int left = 0;
            int top = getTop();
            int right = (int) (mProgress * 1.0f / mMax * getMeasuredWidth() + .5f);//动态计算
            int bottom = getBottom();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }

        super.onDraw(canvas);
    }
}
