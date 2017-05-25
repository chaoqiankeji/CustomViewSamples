package juice.toggle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * canvas.drawBitmap()
 */
public class ToggleView extends View {

	private final static int STATE_NONE = 0;
	private final static int STATE_DOWN = 1;
	private final static int STATE_MOVE = 2;
	private final static int STATE_UP = 3;

	private Bitmap mToggleBackground;
	private Bitmap mToggleSlide;

	private boolean isOpened = false;// 默认关闭状态
	private int mState = STATE_NONE;
	private float mCurrentX;

	public ToggleView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setToggleBackground(int resId) {
		mToggleBackground = BitmapFactory.decodeResource(getResources(), resId);
	}

	public void setToggleSilde(int resId) {
		mToggleSlide = BitmapFactory.decodeResource(getResources(), resId);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 设置的大小和背景大小一致
		if (mToggleBackground != null) {
			// 设置自己的大小
			setMeasuredDimension(mToggleBackground.getWidth()+50,
					mToggleBackground.getHeight());
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 画背景
		if (mToggleBackground != null) {
			canvas.drawBitmap(mToggleBackground, 50, 0, null);
		}

		if (mToggleSlide == null) {
			return;
		}

		int slideWidth = mToggleSlide.getWidth();
		int backgroundWidth = mToggleBackground.getWidth();

		// 按下时 ，
		if (mState == STATE_DOWN) {
			if (!isOpened) {
				// 当现在是关闭状态,
				// 如果点击的是 滑块的左侧（按下的位置 小于 滑块的中间位置）,不动
				if (mCurrentX < slideWidth / 2f) {
					canvas.drawBitmap(mToggleSlide, 50, 0, null);
				} else {
					// 如果点击的是 滑块的右侧（按下的位置 大于 滑块的中间位置）,滑块的中间位置要和 按下的x位置一致
					float left = mCurrentX - slideWidth / 2f;
					if (left > backgroundWidth - slideWidth) {
						left = backgroundWidth - slideWidth;
					}
					canvas.drawBitmap(mToggleSlide, left+50, 0, null);
				}
			} else {
				// TODO:打开
			}
		} else if (mState == STATE_MOVE) {
			// TODO:
		} else if (mState == STATE_UP) {
			// TODO:
		} else {
			// 没有状态
			if (isOpened) {
				// 打开状态
				float left = backgroundWidth - slideWidth;
				canvas.drawBitmap(mToggleSlide, left, 0, null);
			} else {
				// 关闭状态
				canvas.drawBitmap(mToggleSlide, 0, 0, null);
			}
		}
	}

	/**
	 * 负责用户交互并重绘制
	 * @param event
     * @return
     */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mState = STATE_DOWN;
			mCurrentX = event.getX();

			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			mState = STATE_MOVE;
			break;
		case MotionEvent.ACTION_UP:
			mState = STATE_UP;
			break;
		default:
			break;
		}
		return true;
	}
}
