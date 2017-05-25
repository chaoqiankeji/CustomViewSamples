package juice.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * @author chengyong
 * @time 2017/3/27 14:32
 * @des 画基础图形：drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
 *                 drawLine 绘制直线 drawPoin 绘制点
 */

public class DrawBasicView extends View {

    public DrawBasicView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        Paint p = initPaint();

//        drawCircle(canvas, p);
////
//        drawLine(canvas, p);
//        //画笑脸弧线
//        drawArc(canvas, p);
//
//        drawRect(canvas, p);
////
        drawArcAndOval(canvas, p);
////
//        drawSanJiao(canvas, p);
////
////        // 你可以绘制很多任意多边形，比如下面画六连形
//        drawDuoBiao(canvas, p);
////
////        //画圆角矩形
//        drawRoundRect(canvas, p);
////
////        //画贝塞尔曲线
//        drawQuadBeiSaier(canvas, p);
////
////        //画点
//        drawPoint(canvas, p);
////
////        //画图片，就是贴图
//        drawBitmap(canvas, p);
    }

    private void drawBitmap(Canvas canvas, Paint p) {
        canvas.drawText("画图片：", 50, 1000, p);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.btn_circle_update);
        canvas.drawBitmap(bitmap, 250, 1000, p);
    }

    private void drawPoint(Canvas canvas, Paint p) {
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.RED);
        p.setStrokeWidth(22);
        canvas.drawText("画点：", 50, 900, p);
        canvas.drawPoint(160, 990, p);//画一个点
        canvas.drawPoints(new float[]{60, 400, 65, 400, 70, 400}, p);//画多个点
    }

    private void drawQuadBeiSaier(Canvas canvas, Paint p) {
        canvas.drawText("画贝塞尔曲线:", 50, 800, p);
        p.reset();
        p.setTextSize(48);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        Path path2 = new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(250, 710, 770, 1000); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线
    }

    /**
     * ❀带圆角的矩形
     *TODO xfermode  实现图片的剪切
     * @param canvas
     * @param p
     */
    private void drawRoundRect(Canvas canvas, Paint p) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);//注意，一定要禁用硬件加速器
        Bitmap priBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bg2);
        p.reset();
        p.setTextSize(48);
        p.setStyle(Paint.Style.FILL);//充满
        p.setColor(Color.BLUE);
        p.setAntiAlias(true);// 设置画笔的锯齿效果
//        canvas.drawText("画圆角矩形:", 50, 700, p);
//        RectF oval3 = new RectF(80, 60, 1000, 300);// 设置个新的长方形
        canvas.drawCircle(priBmp.getWidth()/2, priBmp.getWidth()/2, priBmp.getWidth()/2, p);//第二个参数是x半径，第三个参数是y半径

        //为画笔设置xFermode风格，只有具有alpha通道的图片才有效
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));   //显示下面的交集
        //画上图片
        canvas.drawBitmap(priBmp, 0, 0, p);

        //最后别忘记取消风格
        p.setXfermode(null);
    }

    private void drawDuoBiao(Canvas canvas, Paint p) {
        p.reset();//重置
        p.setColor(Color.LTGRAY);
        p.setStyle(Paint.Style.STROKE);//设置空心
        Path path1 = new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, p);
    }

    /**
     * 话多变性。
     */
    private void drawSanJiao(Canvas canvas, Paint p) {
        canvas.drawText("画三角形：", 50, 600, p);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }

    private void drawArcAndOval(Canvas canvas, Paint p) {
        canvas.drawText("画扇形和椭圆:", 50, 500, p);
//        /* 设置渐变色 这个正方形的颜色是改变的 */
//        Shader mShader = new LinearGradient(0, 0, 100, 100,
//                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
//                        Color.LTGRAY}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
//        p.setShader(mShader);
////        // p.setColor(Color.BLUE);
//        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
//        canvas.drawArc(oval2, 200, 130, false, p);
////        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
//        //画椭圆，把oval改一下
//        oval2.set(210, 100, 250, 130);
//        canvas.drawOval(oval2, p);


        Path path = new Path();
        //有弧度
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        path.addArc(oval2, 0, 90);
        p.setTextSize(14);
        //文字宽度
//        float textWidth = textPaint.measureText(str);
//        //利用水平偏移量让文字居中
//        int hOffset = (int) (radius * Math.PI / count / 2 - textWidth / 2);
//        //垂直偏移量
//        int vOffset = radius / 2 / 6;
        canvas.drawTextOnPath("我是中国人", path, 0, 0, p);     //很重要；沿着弧形画文本
    }

    private void drawRect(Canvas canvas, Paint p) {
        canvas.drawText("画矩形：", 50, 400, p);
        p.setColor(Color.GRAY);// 设置灰色
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(400, 60, 600, 80, p);// 正方形
    }

    private void drawArc(Canvas canvas, Paint p) {
        canvas.drawText("画笑脸弧线：", 50, 300, p);
        p.setColor(Color.RED);// 设置绿色
        p.setStyle(Paint.Style.FILL);//设置空心
        RectF oval1 = new RectF(400, 300, 580, 680);
//        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
//        oval1.set(190, 20, 220, 40);
//        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
//        oval1.set(160, 30, 210, 60);
//        canvas.drawArc(oval1, 0, 270, false, p);//小弧形
        canvas.drawCircle(200, 300, 30, p);
        ColorDrawable colorDrawable = new ColorDrawable(Color.BLUE);
        colorDrawable.setBounds(100, 300, 800, 500);
        colorDrawable.draw(canvas);

        canvas.drawOval(oval1, p);
    }

    private void drawLine(Canvas canvas, Paint p) {
        canvas.drawText("画线及弧线：", 50, 200, p);
        p.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(400, 200, 650, 200, p);// 画线
        canvas.drawLine(700, 200, 950, 250, p);// 斜线
    }

    private void drawCircle(Canvas canvas, Paint p) {
        canvas.drawText("画圆：", 50, 100, p);// 画文本
        canvas.drawCircle(460, 70, 40, p);// 小圆
        canvas.drawCircle(700, 70, 80, p);// 大圆
    }

    @NonNull
    private Paint initPaint() {
        Paint p = new Paint();
        p.setColor(Color.BLUE);// 设置红色
        p.setTextSize(44);
        p.setAntiAlias(true);
        return p;
    }



}
