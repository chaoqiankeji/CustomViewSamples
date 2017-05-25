package juice.zuanpanview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by vera on 2016/12/23 0023.
 */

public class ZuanPan extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    /**
     * 用于绘制子线程
     */
    private Thread thread;
    /**
     * 线程的控制开关
     */
    private boolean isRunning = true;
    /**
     * 文字
     */
    private String[] strs = new String[]{"单反相机", "IPAD", "恭喜发财", "iphone", "服装一套", "恭喜发财"};
    /**
     * 图片
     */
    private int[] imgs = new int[]{R.drawable.danfan, R.drawable.ipad, R.drawable.f015, R.drawable.iphone, R.drawable.meizi, R.drawable.f040};

    private Bitmap[] bitmaps;
    /**
     * 颜色
     */
    private int[] colors = new int[]{0XFFFFC300, 0XFFF17E01, 0XFFFFC300, 0XFFF17E01, 0XFFFFC300, 0XFFF17E01};
    /**
     * 数量
     */
    private int count = 6;
    /**
     * 整个盘块
     */
    private RectF rectF = new RectF();
    /**
     * 直径
     */
    private int radius;
    /**
     * 绘制盘块的画笔
     */
    private Paint arcPaint;

    /**
     * 绘制文本
     *
     * @param context
     */
    private Paint textPaint;
    /**
     * 盘块滚动的速度
     */
    private double speed;

    /**
     * 起始角度
     * volatile表示线程间的可见性
     *
     * @param context
     */
    private volatile float startAngle = 0;   //从主内存获取最新值，线程的工作内存去拿主内存的最新值，如果是多个线程并不能解决并发问题
    /**
     * 是否点击了停止
     */
    private boolean isShouldEnd;
    /**
     * 转盘中心位置
     */
    private int center;
    /**
     * 转盘边距,以paddingLeft为准
     */
    private int padding;

    /**
     * 背景
     *
     * @param context
     */
    private Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
    /**
     * 字体大小
     */
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    public ZuanPan(Context context) {
        this(context, null);
    }

    public ZuanPan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ////-----------holder
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);//可点击
        setFocusableInTouchMode(true);//可获取焦点
        setKeepScreenOn(true);//设置常量
        /////-------
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置成正方形
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        padding = getPaddingLeft();
        //直径
        radius = width - padding * 2;
        //中心点
        center = width / 2;
        setMeasuredDimension(width, width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setDither(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);

        //盘块
        rectF = new RectF(padding, padding, padding + radius, padding + radius);
        //初始化图片
        bitmaps = new Bitmap[count];
        for (int i = 0; i < count; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), imgs[i]);
        }

        //开启线程
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            if (end - start < 50) {
                try {
//                    Thread.sleep(50 - (end - start));
                    Thread.sleep(1);  //控制重绘的间隔，不突兀，但是性能可能差些
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private void draw() {
        canvas = surfaceHolder.lockCanvas();
        try {
            if (canvas != null) {
                Log.i("juice","在绘制中");
                //绘制
                drawBg();
                drawPan();               //根据起始角度一次画完每个盘块
                startAngle += speed;    //不断改变开始绘制的角度+循环绘制=动态旋转效果；
                //判断是否点击停止
                if (isShouldEnd) {     //点击停止时=true;速度不断减小，开始绘制的角度就不会再变化，所以看上去就没转了。
                    speed -= 1;
                }
                if (speed <= 0) {
                    speed = 0;
                    isShouldEnd = false;
                }

            }
        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                //释放canvas
                surfaceHolder.unlockCanvasAndPost(canvas);
                Log.i("juice","在绘制结束");
            }
        }

    }

    /**
     * 开始
     */
    public void luckyStart(int index) {
//        //计算每一项的角度
//        float angel=360/count;
//        //计算每一项的中奖范围（当前index）
//        //1-150-210
//        //0-210-270
//        float from=270-(index+1)*angel;   //每一块小盘都有一个起始、结束的角度；可以形成公式
//        float end=from+angel;
//        //设置停下来需要旋转的距离
//        float targetFrom=4*360+from;
//        float targetEnd=4*350+end;
//        //等差数列
//        float v1= (float) ((-1+Math.sqrt(1+8*targetFrom))/2);
//        float v2= (float) ((-1+Math.sqrt(1+8*targetEnd))/2);
//        speed=v1+Math.random()*(v2-v1);      //是为了能确定停在哪里
///----------固定速度
        speed= Math.random()*10;     //随便停哪
//        speed=150;                         //能确定停在哪，只需控制速度即可，每次停的位置都是一样的。
        isShouldEnd=false;
    }
    /**
     *停止
     */
    public void luckyEnd() {
        startAngle=0;
        isShouldEnd=true;
    }

    /**
     * 转盘是否还在旋转
     */
    public boolean isStart(){
        return speed!=0;
    }
    public boolean isShouldEnd(){
        return isShouldEnd;
    }





    /************以下是绘制具体的模块，结合完成一次抽奖转盘的绘制，要让其实现旋转效果，只需改变起始角度不断重绘即可*******************/
    private void drawPan() {

        float tmpAngle = startAngle;
        float sweepAngle = 360 / count;
        for (int i = 0; i < count; i++) {
            arcPaint.setColor(colors[i]);
            //绘制盘块
            canvas.drawArc(rectF, tmpAngle, sweepAngle, true, arcPaint);
            //绘制文本
            drawText(tmpAngle, sweepAngle, strs[i]);
            //绘制图片
            drawIcon(tmpAngle, sweepAngle, bitmaps[i]);
            tmpAngle += sweepAngle;
        }
    }

    /**
     * 绘制ICON
     *
     * @param tmpAngle
     * @param sweepAngle
     * @param bitmap
     */
    private void drawIcon(float tmpAngle, float sweepAngle, Bitmap bitmap) {
        //设置图片的宽度
        int iconWidth = radius / 8;
        //一度=PI/180
        float angle = (float) ((tmpAngle + 360 / count / 2) * Math.PI / 180);
        int x = (int) (center + radius / 2 / 2 * Math.cos(angle));
        int y = (int) (center + radius / 2 / 2 * Math.sin(angle));
        Rect rect = new Rect(x - iconWidth / 2, y - iconWidth / 2, x + iconWidth / 2, y + iconWidth / 2);  //定位不同矩形的位置控制不同bitmap的位置
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    /**
     * 绘制每个盘块的文本
     *
     * @param tmpAngle
     * @param sweepAngle
     * @param str
     */
    private void drawText(float tmpAngle, float sweepAngle, String str) {
        Path path = new Path();
        //有弧度
        path.addArc(rectF, tmpAngle, sweepAngle);
        //文字宽度
        float textWidth = textPaint.measureText(str);
        //利用水平偏移量让文字居中
        int hOffset = (int) (radius * Math.PI / count / 2 - textWidth / 2);
        //垂直偏移量
        int vOffset = radius / 2 / 6;
        canvas.drawTextOnPath(str, path, hOffset, vOffset, textPaint);     //很重要；沿着弧形画文本
    }

    /**
     * 绘制背景
     */
    private void drawBg() {

        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bgBitmap, null, new Rect(padding / 2, padding / 2, getMeasuredWidth() - padding / 2, getMeasuredHeight() - padding / 2), null);
    }
}
