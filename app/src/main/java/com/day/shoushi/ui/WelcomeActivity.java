package com.day.shoushi.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.day.shoushi.R;
import com.day.shoushi.config.Constants;

public class WelcomeActivity extends BaseActivity {

	public static final String TAG = WelcomeActivity.class.getSimpleName();

	private ImageView s_iv = null;
	@Override
	protected void findViewById() {
		s_iv = (ImageView) findViewById(R.id.ss_logo1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Constants.SCREEN_DENSITY = metrics.density;
		Constants.SCREEN_HEIGHT = metrics.heightPixels;
		Constants.SCREEN_WIDTH = metrics.widthPixels;

		mHandler = new Handler(getMainLooper());
		findViewById();
		initView();
		setOnClick();
	}
	private void setOnClick(){
		s_iv.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(WelcomeActivity.this,"123",Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	protected void initView() {
		Animation translate = AnimationUtils.loadAnimation(this,
				R.anim.splash_loading);
		translate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//openActivity(HomeActivity.class);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				//WelcomeActivity.this.finish();
			}
		});
		s_iv.setAnimation(translate);
	}

}
