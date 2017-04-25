package com.day.shoushi.myview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	private View inner; // ����View
	private static final int DEFAULT_POSITION = -1;
	private float y = DEFAULT_POSITION;// ���ʱy������
	private Rect normal = new Rect();

	// �������뼰����
	private float xDistance, yDistance, xLast, yLast;

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * ����XML������ͼ������ɣ��ú�����������ͼ�������ã�����������ͼ������֮�� ��ʹ���า����onFinishInflate
	 * ������ҲӦ�õ��ø���ķ����� ʹ�ø÷�������ִ��
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner == null) {
			return super.onTouchEvent(ev);
		} else {
			commOnTouchEvent(ev);
		}

		return super.onTouchEvent(ev);
	}

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			y = ev.getY();
			break;
		case MotionEvent.ACTION_UP:

			if (isNeedAnimation()) {
				animation();
			}

			y = DEFAULT_POSITION;
			break;

		/**
		 * �ų���һ���ƶ����㣬��Ϊ��һ���޷���֪y��ߣ���MotionEvent.ACTION_DOWN�л�ȡ������
		 * ��Ϊ��ʱ��MyScrollView��Tocuhʱ�䴫�ݵ���ListView�ĺ���item���档���Դӵڶ��ο�ʼ����
		 * Ȼ������ҲҪ���г�ʼ�������ǵ�һ���ƶ���ʱ���û���������㣬֮���¼׼ȷ�˾�����ִ��
		 */
		case MotionEvent.ACTION_MOVE:
			float preY = y;
			float nowY = ev.getY();
			if (isDefaultPosition(y)) {
				preY = nowY;
			}
			int deltaY = (int) (preY - nowY);
			scrollBy(0, deltaY);
			y = nowY;
			// �����������ϻ�������ʱ�Ͳ����ٹ�������ʱ�ƶ�����
			if (isNeedMove()) {
				if (normal.isEmpty()) {
					// ���������Ĳ���λ��
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());

				}
				// �ƶ�����
				inner.layout(inner.getLeft(), inner.getTop() - deltaY,
						inner.getRight(), inner.getBottom() - deltaY);
			}
			break;

		default:
			break;
		}
	}

	// ���������ƶ�

	public void animation() {
		// �����ƶ�����
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// ���ûص������Ĳ���λ��
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);

		normal.setEmpty();

	}

	// �Ƿ���Ҫ��������
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	// �Ƿ���Ҫ�ƶ�����
	public boolean isNeedMove() {

		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
	}

	// ����Ƿ���Ĭ��λ��
	private boolean isDefaultPosition(float position) {
		return position == DEFAULT_POSITION;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}

}
