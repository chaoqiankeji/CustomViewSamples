package juice.custom_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import juice.circleprogressview.CircleProgressView;
import juice.progressbutton.ProgressBtn;
import juice.toggle.ToggleView;
import juice.zexianview.ZeXianView;
import juice.zuanpanview.ZuanPan;
import rx.functions.Action1;

/**
 * @author chengyong
 * @time 2017/3/27 15:39
 * @des
 */
public class MainActivity extends AppCompatActivity {

    private ImageView iv_start;
    private ZuanPan zuanpan;
    private ZeXianView zeXianView;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        layout = (LinearLayout) findViewById(R.id.root);
        zeXianView = (ZeXianView)findViewById(R.id.zexian);
        zuanpan = (ZuanPan)findViewById(R.id.zuanpan);
//        LuckyMonkeyPanelView luck = (LuckyMonkeyPanelView)findViewById(R.id.luck);
        iv_start = (ImageView)findViewById(R.id.iv_start);
        zeXianView.setVisibility(View.GONE);
//        zuanpan.setVisibility(View.GONE);
        iv_start.setVisibility(View.GONE);

        /****************debug*****************/
//        drawBasicViewTest(layout);
//        drawToggleViewTest(layout);
//        drawProgressButtonTest(layout);
//        drawCircleProgressButtonTest(layout);
//        drawZeXianTest(zeXianView);

//         drawZuanPanTest(zuanpan);
//         drawZuanPanTest(luck);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 自定义View基本API
     * @param layout
     */
    private void drawBasicViewTest(LinearLayout layout) {
        final DrawBasicView view=new DrawBasicView(this);
        RxView.clicks(view)
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                               @Override
                               public void call(Void aVoid) {
                                   Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                                   Log.i("juice","点击了；！！！");
                               }
                           }

                );
        view.invalidate();
        layout.addView(view);
    }

    /**
     * Toggle test
     * @param layout
     */
    private void drawToggleViewTest(LinearLayout layout) {
        final ToggleView mToggleView=new ToggleView(this);
        // 设置背景,滑块
        mToggleView.setToggleBackground(juice.toggle.R.mipmap.switch_background);
        mToggleView.setToggleSilde(juice.toggle.R.mipmap.slide_button_background);
        mToggleView.invalidate();
        layout.addView(mToggleView);
    }
    /**
     * ProgressButton test
     * @param layout
     */
    private void drawProgressButtonTest(final LinearLayout layout) {
        final ProgressBtn progressBtn=new ProgressBtn(this);
        // 设置背景,滑块
        progressBtn.setBackgroundResource(R.mipmap.switch_background);
        progressBtn.setMax(100);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i("juice","执行任务");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Random random=new Random();
                        int i=random.nextInt(100);
                        if(i>97){
                            Log.i("juice","随机数大于97；绘制停止！！！！");
                            timer.cancel();
                            return;
                        }
                        progressBtn.setProgress(i);
                        progressBtn.invalidate();
                        layout.removeAllViews();
                        layout.addView(progressBtn);
                    }
                });

            }
        },50,250);
    }

    /**
     * CircleProgressButton test
     * @param layout
     */
    private void drawCircleProgressButtonTest(final LinearLayout layout) {
        final CircleProgressView circleProgressView = new CircleProgressView(this);
        circleProgressView.setMax(100);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i("juice","执行任务");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Random random=new Random();
                        int i=random.nextInt(100);
                        if(i>97){
                            Log.i("juice","随机数大于97；绘制停止！！！！");
                            timer.cancel();
                            return;
                        }
                        circleProgressView.setProgress(i);
                        circleProgressView.invalidate();
                        layout.removeAllViews();
                        layout.addView(circleProgressView);
                    }
                });
            }
        },50,250);
    }

    /**
     * ZeXian test
     * @param zeXianView
     */
    private void drawZeXianTest(ZeXianView zeXianView) {
        zeXianView.setVisibility(View.VISIBLE);
//        zeXianView.setMaxScore(100);
//        zeXianView.setMinScore(30);
//        zeXianView.setScore(new int[]{44,46,47,47,77,88});
        zeXianView.invalidate();
    }

    /**
     * zuanpan test--------------SurfaceView
     * @param zuanPan
     */
    private void drawZuanPanTest(final ZuanPan zuanPan) {
        zuanPan.setVisibility(View.VISIBLE);
        iv_start.setVisibility(View.VISIBLE);
//        iv_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!zuanPan.isGameRunning()) {
//                    zuanPan.startGame();
//                } else {
//                    int stayIndex = new Random().nextInt(8);
//                    Log.e("LuckyMonkeyPanelView", "====stay===" + stayIndex);
//                    zuanPan.tryToStop(stayIndex);
//                }
//            }
//        });
    }
}
