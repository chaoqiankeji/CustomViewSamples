package juice.toggle;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private ToggleView mToggleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mToggleView = (ToggleView) findViewById(R.id.toggle);

		// 设置背景,滑块
		mToggleView.setToggleBackground(R.drawable.switch_background);
		mToggleView.setToggleSilde(R.drawable.slide_button_background);
	}

}
