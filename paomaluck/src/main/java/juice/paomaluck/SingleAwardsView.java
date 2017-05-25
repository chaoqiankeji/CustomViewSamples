package juice.paomaluck;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by jeanboy on 2017/4/20.
 */

public class SingleAwardsView extends FrameLayout implements ItenFocusListener {

    private FrameLayout overlay;
    private ImageView mAwardIconView;
    private TextView mAwardDes;

    public SingleAwardsView(Context context) {
        this(context, null);
    }

    public SingleAwardsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleAwardsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);
        overlay = (FrameLayout)findViewById(R.id.overlay);
        mAwardIconView = (ImageView)findViewById(R.id.award_iv_icon);
        mAwardDes = (TextView)findViewById(R.id.award_tv_des);
    }

    @Override
    public void setFocus(boolean isFocused) {
        ImageView focusView=null;
        if (overlay != null) {
            if( focusView==null){
                focusView =new ImageView(getContext());
                focusView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                focusView.setBackgroundResource(R.drawable.award_light);
            }
            if(isFocused) {
                Log.e("award", "添加光圈" );
                overlay.addView(focusView);
            }else {
                Log.e("award", "移除光圈" );
                overlay.removeAllViews();
            }
        }
    }

    @Override
    public void setAwardMessage(String imageUrl, String awardDes) {
//        GlobalConfig.combineImageUrl(imageUrl)
        Glide.with(getContext()).load(imageUrl).crossFade().placeholder(R.mipmap.push).into(mAwardIconView);
        mAwardDes.setText(awardDes);
    }
}
