package com.day.shoushi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import com.day.shoushi.R ;

public class BaseFragment extends FragmentActivity {
	/**
	 * @param aty
	 *            Ԫactivity
	 * @param clazz
	 */
	protected void router(Activity aty, Class<? extends Activity> clazz) {
		Intent intent = new Intent(aty, clazz);
		startActivity(intent);
	}

	/**
	 * 
	 * @param aty
	 *            Ԫactivity
	 * @param clazz
	 * @param requestCode
	 */
	protected void router(Activity aty, Class<? extends Activity> clazz,
			int requestCode) {
		Intent intent = new Intent(aty, clazz);
		startActivityForResult(intent, requestCode);
	}

	protected void addFragments(int rId, Fragment[] fragments) {
		FragmentTransaction tx = getTransaction();
		for (Fragment f : fragments) {
			tx.add(rId, f);
		}
		tx.commit();
	}

	protected void hideFragments(Fragment[] fragments) {
		FragmentTransaction tx = getTransaction();
		for (Fragment f : fragments) {
			tx.hide(f);
		}
		tx.commit();
	}

	/**
	 * 
	 * @param fragment
	 */
	protected void showFragment(Fragment fragment) {
		FragmentTransaction tx = getTransaction();
		tx.show(fragment);
		tx.commit();
	}

	/**
	 * @return
	 */
	protected FragmentTransaction getTransaction() {
		return getManager().beginTransaction();
	}

	/**
	 * @return
	 */
	protected FragmentManager getManager() {
		return getSupportFragmentManager();
	}

	public static void initSystemBar(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(activity, true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.tab_menu_bg);
	}

	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
