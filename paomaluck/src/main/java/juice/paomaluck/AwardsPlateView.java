package juice.paomaluck;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Random;

/**
 * Created by jeanboy on 2017/4/20.
 */

public class AwardsPlateView extends FrameLayout implements View.OnClickListener {

    private SingleAwardsView itemView1, itemView2, itemView3, startView,
            itemView4, itemView6,
            itemView7, itemView8, itemView9;

    private ItenFocusListener[] itemViewArr = new ItenFocusListener[8];
    private int currentIndex = 0;
    private int currentTotal = 0; //循环次数
    private int stayIndex = 0;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 150;
    private static final int MIN_SPEED = 50;
    private int currentSpeed = DEFAULT_SPEED;

    public AwardsPlateView(@NonNull Context context) {
        this(context, null);
    }

    public AwardsPlateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AwardsPlateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_mokey_panel, this);
        setupView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void setupView() {
        itemView1 = (SingleAwardsView) findViewById(R.id.item1);
        itemView2 = (SingleAwardsView) findViewById(R.id.item2);
        itemView3 = (SingleAwardsView) findViewById(R.id.item3);
        itemView4 = (SingleAwardsView) findViewById(R.id.item4);
        startView = (SingleAwardsView) findViewById(R.id.start);
        itemView6 = (SingleAwardsView) findViewById(R.id.item6);
        itemView7 = (SingleAwardsView) findViewById(R.id.item7);
        itemView8 = (SingleAwardsView) findViewById(R.id.item8);
        itemView9 = (SingleAwardsView) findViewById(R.id.item9);

        itemViewArr[0] = itemView4;
        itemViewArr[1] = itemView1;
        itemViewArr[2] = itemView2;
        itemViewArr[3] = itemView3;
        itemViewArr[4] = itemView6;
        itemViewArr[5] = itemView9;
        itemViewArr[6] = itemView8;
        itemViewArr[7] = itemView7;
        itemViewArr[1].setFocus(true);
        startView.setOnClickListener(this);

        String icon_url1="http://gcenter.ol.ttigame.cn/uploads/icons/2/d/2d7b40be35f5dc8ce2c3243f2c5340f1.png";
        String icon_url2="http://gcenter.ol.ttigame.cn/uploads/icons/6/9/6928300c01b1494ebab80536b38112c6.png";
        String icon_url3="http://image.game.uc.cn/2015/9/22/11011499_.png";
        for (ItenFocusListener item : itemViewArr) {  //TODO 请求网络成功绑定数据
            item.setAwardMessage(icon_url2, "我是奖品");
        }
        startView.setAwardMessage(icon_url3,"开始抽奖");
    }
    /**
     * 请求网络成功绑定数据
     *
     * @return
     */
    public void setAwardData() {
    }

    /**
     * 动态改变间歇时间，控制速度
     *
     * @return
     */
    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {  //点击停止后的速度控制
            currentSpeed += 10;  //速度由快(50ms)变慢（150ms）-->最后稳定（150ms循环一次）
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
            Log.e("juice", "speed请求网络完成后的循环：当前循环次数currentTotal==" + currentTotal+"==当前的间歇时间是==" + currentSpeed);
        } else {
            if (currentTotal / itemViewArr.length > 0) {
                currentSpeed -= 10;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED; //速度由慢（150ms）变快(50ms)-->最后稳定（50ms循环一次）
            }
            Log.e("juice", "speed正常在循环：当前循环次数currentTotal==" + currentTotal+"==当前的间歇时间是==" + currentSpeed);
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime()); //改变时间来控制循环的速度
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= itemViewArr.length) {
                                currentIndex = 0; //TODO 是否控制转的圈数
                            }
                            itemViewArr[preIndex].setFocus(false);
                            itemViewArr[currentIndex].setFocus(true);
                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false; //当前选中的与设定一致,确定结果，结束循环
                            }
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 点击抽奖、网络请求返回中奖结果
     * @des 供外界调用
     * @param position
     */
    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }

    @Override
    public void onClick(View view) {
        if (!isGameRunning()) {
            startGame();
        } else {
            int stayIndex = new Random().nextInt(8);
            Log.e("juice", "index可能的结果索引是：" + stayIndex);
            tryToStop(stayIndex);
        }
    }
}
