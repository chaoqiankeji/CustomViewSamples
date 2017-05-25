package juice.zuanpanview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by vera on 2016/12/23 0023.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    /**
     * 用于绘制子线程
     */
    private Thread thread;
    /**
     * 线程的控制开关
     */
    private boolean isRunning;
    public SurfaceViewTemplate(Context context) {
        this(context,null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);//可点击
        setFocusableInTouchMode(true);//可获取焦点
        setKeepScreenOn(true);//设置常量
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //开启线程
        isRunning=true;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning=false;
    }

    @Override
    public void run() {
        while (isRunning){
            draw();
        }
    }

    private void draw() {
        canvas=surfaceHolder.lockCanvas();
        try {
            if (canvas!=null){
                //绘制
                canvas.drawCircle(200,200,78,new Paint(Color.GREEN));
            }
        }catch (Exception e){
        }finally {
            if (canvas!=null){
                //释放canvas
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }
}
