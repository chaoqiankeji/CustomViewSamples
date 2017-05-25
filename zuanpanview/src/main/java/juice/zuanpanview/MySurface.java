package juice.zuanpanview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author chengyong
 * @time 2017/4/7 10:52
 * @des ${TODO}
 */

public class MySurface extends SurfaceView implements SurfaceHolder.Callback{
    public MySurface(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        Canvas canvas = holder.lockCanvas();
        canvas.drawCircle(300,300,33,new Paint(Color.GREEN));
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
