package fr.utc.assos.uvweb;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * A very simple custom {@link ViewPager} that allows to disable swipe gesture.
 * This is particularly useful on tablets (two-pane mode) as the fragments contained in the ViewPager
 * are just a small part of the shown UI. Thus we don't want the user to swipe its content, we'd rather like
 * him to click on the tabs.
 */
public class UVwebViewPager extends ViewPager {

	private boolean mIsSwipeDisabled;

	public UVwebViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, false);
	}

	public UVwebViewPager(Context context, AttributeSet attrs, boolean disableSwipe) {
		super(context, attrs);
		mIsSwipeDisabled = disableSwipe;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return !mIsSwipeDisabled && super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return !mIsSwipeDisabled && super.onInterceptTouchEvent(event);
	}

	public void setDisableSwipe(boolean disableSwipe) {
		mIsSwipeDisabled = disableSwipe;
	}

	public boolean getIsSwipeDisabled() {
		return mIsSwipeDisabled;
	}
}
