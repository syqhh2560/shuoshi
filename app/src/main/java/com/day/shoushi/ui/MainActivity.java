package com.day.shoushi.ui;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TextView;

import com.day.shoushi.R;
public class MainActivity extends BaseFragment {

	public static final String TAG = "FragmentTabs";
	public static final String LOGIN_SUCCESS = "loginSuccess";

	public static final String TAB_HOME = "home";
	public static final String TAB_YUGAO = "yugao";
	public static final String TAB_MORE = "more";

	private TextView mHomeTab;
	private TextView mYugaoTab;
	private TextView mMoreTab;


	private Drawable mHomePressed;
	private Drawable mHomeNormal;
	private Drawable mYugaoPressed;
	private Drawable mYugaoNormal;
	private Drawable mMorePressed;
	private Drawable mMoreNormal;

	//private BadgeView bgView;
	//public List sharedList;// 保存更新的数据 条数 "update;;1"
	private FragmentTabHost mTabHost;

	private Handler mHandler = new Handler(Looper.getMainLooper());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		 //initSystemBar(this);

		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//
		View indicator = getIndicatorView(TAB_HOME);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME)
				.setIndicator(indicator), HomeFragment.class, null);

		indicator = getIndicatorView(TAB_YUGAO);
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB_YUGAO).setIndicator(indicator),
				ZiLiaoFragment.class, null);

//		indicator = getIndicatorView(TAB_MORE);
//		mTabHost.addTab(mTabHost.newTabSpec(TAB_MORE).setIndicator(indicator),
//				null, null);
//
//		indicator = getIndicatorView(TAB_HUIYI);
//		mTabHost.addTab(mTabHost.newTabSpec(TAB_HUIYI).setIndicator(indicator),
//				HuiYiFragment.class, null);
//
		indicator = getIndicatorView(TAB_MORE);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MORE).setIndicator(indicator),
				SettingFragment.class, null);
//
		// mUnread = (TextView) findViewById(R.id.unread);
//
		mTabHost.getTabWidget().setDividerDrawable(null);

		mTabHost.setOnTabChangedListener(listener);
		listener.onTabChanged(TAB_MESSAGE);

		mTabHost.getTabWidget().getChildTabViewAt(2)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						showPop();
					}
				});
//		bgView = new BadgeView(this, mMessageTab);
	}

//	TalkAboutPopupWindow menuWindow;
//
//	public void showPop() {
//
//		menuWindow = new TalkAboutPopupWindow(WelcomeActivity.this);
//		// 显示窗口
//		menuWindow.showAtLocation(findViewById(R.id.main_layout),
//				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
//	}
//
//	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
//		@Override
//		public void onTabChanged(String tabId) {
//			if (TAB_MESSAGE.equals(tabId)) {
//				setMessageText(true);
//				setZiLiaoText(false);
//				setHuiYiText(false);
//				setMoreText(false);
//				return;
//			}
//			if (TAB_ZILIAO.equals(tabId)) {
//				setMessageText(false);
//				setZiLiaoText(true);
//				setHuiYiText(false);
//				setMoreText(false);
//				return;
//			}
//			if (TAB_HUIYI.equals(tabId)) {
//				setMessageText(false);
//				setZiLiaoText(false);
//				setHuiYiText(true);
//				setMoreText(false);
//				return;
//			}
//			if (TAB_MORE.equals(tabId)) {
//				setMessageText(false);
//				setZiLiaoText(false);
//				setHuiYiText(false);
//				setMoreText(true);
//				return;
//			}
//		}
//	};
////	private int count = 0;
////    private int firClick = 0;
////    private int secClick = 0;
////    private int flage = 0;
//
//	private View getIndicatorView(String tab) {
//		View tabView = View.inflate(this, R.layout.main_tab_item, null);
//		TextView indicator = (TextView) tabView.findViewById(R.id.tab_text);
//		Drawable drawable;
//
//		if (tab.equals(TAB_MESSAGE)) {
//			indicator.setText("首页");
//			drawable = getResources().getDrawable(
//					R.drawable.tab_icon_message_normal);
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			indicator.setCompoundDrawables(null, drawable, null, null);
//			mMessageTab = indicator;
////			count++;
////            if(count == 1){
////                firClick = (int) System.currentTimeMillis();
////
////            } else if (count == 2){
////                secClick = (int) System.currentTimeMillis();
////                if(secClick - firClick < 1000){
////                       //双击事件
////                	HomeFragment.selectAllProducts();
////                }
////                count = 0;
////                firClick = 0;
////                secClick = 0;
////            }
//		} else if (tab.equals(TAB_ZILIAO)) {
//			indicator.setText("资料");
//			drawable = getResources().getDrawable(
//					R.drawable.tab_icon_message_normal);
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			indicator.setCompoundDrawables(null, drawable, null, null);
//			mZiLiaoTab = indicator;
//		} else if (tab.equals(TAB_HUIYI)) {
//			indicator.setText("会议");
//			drawable = getResources().getDrawable(
//					R.drawable.tab_icon_message_normal);
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			indicator.setCompoundDrawables(null, drawable, null, null);
//			mHuiyiTab = indicator;
//		} else if (tab.equals(TAB_SEND)) {
//
//			View tabView_s = View.inflate(this, R.layout.main_tab_item_s, null);
//			// TextView indicato_sr = (TextView) tabView_s
//			// .findViewById(R.id.tab_text_s);
//			// Drawable d = getResources().getDrawable(
//			// R.drawable.tabbar_compose_icon_add_highlighted);
//			// d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//			// indicato_sr.setCompoundDrawables(null, d, null, null);
//
//			return tabView_s;
//
//		} else if (tab.equals(TAB_MORE)) {
//			indicator.setText("设置");
//			drawable = getResources().getDrawable(
//					R.drawable.tab_icon_setting_normal);
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			indicator.setCompoundDrawables(null, drawable, null, null);
//			mMoreTab = indicator;
//		}
//		return tabView;
//	}
//
//	private void setMessageText(boolean isSelected) {
//		Drawable drawable = null;
//		if (isSelected) {
//			mMessageTab.setTextColor(getResources().getColor(
//					R.color.tab_pressed_color));
//			if (mMessagePressed == null) {
//				mMessagePressed = getResources().getDrawable(
//						R.drawable.tab_icon_message_pressed);
//			}
//			drawable = mMessagePressed;
//		} else {
//			mMessageTab.setTextColor(getResources().getColor(
//					R.color.tab_normal_color));
//			if (mMessageNormal == null) {
//				mMessageNormal = getResources().getDrawable(
//						R.drawable.tab_icon_message_normal);
//			}
//			drawable = mMessageNormal;
//		}
//		if (drawable != null) {
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			mMessageTab.setCompoundDrawables(null, drawable, null, null);
//		}
//	}
//
//	private void setZiLiaoText(boolean isSelected) {
//		Drawable drawable = null;
//		if (isSelected) {
//			mZiLiaoTab.setTextColor(getResources().getColor(
//					R.color.tab_pressed_color));
//			if (mZiLiaoPressed == null) {
//				mZiLiaoPressed = getResources().getDrawable(
//						R.drawable.tab_icon_ziliao_pressed);
//			}
//			drawable = mZiLiaoPressed;
//		} else {
//			mZiLiaoTab.setTextColor(getResources().getColor(
//					R.color.tab_normal_color));
//			if (mZiLiaoNormal == null) {
//				mZiLiaoNormal = getResources().getDrawable(
//						R.drawable.tab_icon_ziliao_normal);
//			}
//			drawable = mZiLiaoNormal;
//		}
//		if (drawable != null) {
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			mZiLiaoTab.setCompoundDrawables(null, drawable, null, null);
//		}
//	}
//	private void setHuiYiText(boolean isSelected) {
//		Drawable drawable = null;
//		if (isSelected) {
//			mHuiyiTab.setTextColor(getResources().getColor(
//					R.color.tab_pressed_color));
//			if (mHuiYiPressed == null) {
//				mHuiYiPressed = getResources().getDrawable(
//						R.drawable.tab_icon_huiyi_pressed);
//			}
//			drawable = mHuiYiPressed;
//		} else {
//			mHuiyiTab.setTextColor(getResources().getColor(
//					R.color.tab_normal_color));
//			if (mHuiYiNormal == null) {
//				mHuiYiNormal = getResources().getDrawable(
//						R.drawable.tab_icon_huiyi_normal);
//			}
//			drawable = mHuiYiNormal;
//		}
//		if (null != drawable) {// 此处出现过NP问题，加保护
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			mHuiyiTab.setCompoundDrawables(null, drawable, null, null);
//		}
//
//	}
//	private void setMoreText(boolean isSelected) {
//		Drawable drawable = null;
//		if (isSelected) {
//			mMoreTab.setTextColor(getResources().getColor(
//					R.color.tab_pressed_color));
//			if (mMorePressed == null) {
//				mMorePressed = getResources().getDrawable(
//						R.drawable.tab_icon_setting_pressed);
//			}
//			drawable = mMorePressed;
//		} else {
//			mMoreTab.setTextColor(getResources().getColor(
//					R.color.tab_normal_color));
//			if (mMoreNormal == null) {
//				mMoreNormal = getResources().getDrawable(
//						R.drawable.tab_icon_setting_normal);
//			}
//			drawable = mMoreNormal;
//		}
//		if (null != drawable) {// 此处出现过NP问题，加保护
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			mMoreTab.setCompoundDrawables(null, drawable, null, null);
//		}
//
//	}
//
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		numberVisible();
//		super.onStart();
//	}
//
//	/**
//	 * 数字显示
//	 */
//	public void numberVisible() {
//		sharedList = ApplicationData.getSharedUpdateAddProduct(this);
//		if (sharedList != null && sharedList.size() > 0) {
//
//			bgView.setText(sharedList.size() + "");
//			bgView.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
//			bgView.setBadgeMargin(30, 10);
//			bgView.setBadgeBackgroundColor(Color.parseColor("#ff0000"));
//			TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
//			anim.setInterpolator(new BounceInterpolator());
//			anim.setDuration(1000);
//			bgView.toggle(anim, null);
//			bgView.setVisibility(View.VISIBLE);
//		} else {
//			bgView.setText("");
//			bgView.setVisibility(View.GONE);
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		// Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
//		// // TODO Auto-generated method stub
//		// if(menuWindow!=null&&menuWindow.isShowing()){
//		// //hideView();
//		// if (menuWindow.pop_sel.isShown()==false) {
//		// menuWindow.pop_sel.setVisibility(View.VISIBLE);
//		// }
//		// if (menuWindow.pop_close.isShown()==false) {
//		// menuWindow.pop_close.setVisibility(View.VISIBLE);
//		// }
//		// }
//		super.onBackPressed();
//
//	}

}