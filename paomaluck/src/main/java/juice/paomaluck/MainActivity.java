package juice.paomaluck;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

public class MainActivity extends Activity {

    private AwardsPlateView luck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        String icon_url1="http://gcenter.ol.ttigame.cn/uploads/icons/2/d/2d7b40be35f5dc8ce2c3243f2c5340f1.png";
        luck = (AwardsPlateView)findViewById(R.id.luck);
        ImageView test = (ImageView)findViewById(R.id.iv);
        Glide.with(this).load(icon_url1).crossFade().placeholder(R.mipmap.push).into(test);
//        Glide.with(this).load(icon_url1).into(test);
    }

}
