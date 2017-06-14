package tut.flightbookingsystem.adapter;

/**
 * Created by joaonzangoii on 4/12/17.
 */

import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//Created by Aspsine on 2015/9/7.
public abstract class BaseLoopPagerAdapter extends PagerAdapter
        implements ViewPager.OnPageChangeListener, View.OnTouchListener, Runnable {

    private static int DEFAULT_DELAY_MILLIS = 5000;

    private ViewPager mViewPager;

    private Handler mHandler;

    private List<View> mViews;

    private List mList;

    private int mChildCount;

    private int mDelayMillis = DEFAULT_DELAY_MILLIS;

    private boolean mRunning;

    public BaseLoopPagerAdapter() {

    }

    public BaseLoopPagerAdapter(final ViewPager viewPager) {
        mHandler = new Handler(Looper.getMainLooper());
        mList = new ArrayList<>();
        mViews = new LinkedList<>();

        mViewPager = viewPager;
        mViewPager.setOnTouchListener(this);
    }

    /**
     * get the item count or pager count
     *
     * @return
     * @see #notifyDataSetChanged()
     */
    public abstract int getPagerCount();

    /**
     * get the item
     *
     * @param position
     * @return
     * @see #notifyDataSetChanged()
     */
    public abstract Object getItem(int position);

    /**
     * get the viewpager item view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     * @see #instantiateItem(ViewGroup, int)
     */
    public abstract View getView(final int position,
                                 final View convertView,
                                 final ViewGroup parent);

    /**
     * @see #onPageSelected(int)
     */
    public abstract void onPageItemSelected(final int position);

    @Override
    public void notifyDataSetChanged() {
        int fixedCount = getPagerCount();
        if (fixedCount <= 0) {
            return;
        } else if (fixedCount == 1) {
            if (fixedCount != mList.size()) {
                mList.clear();
                mList.add(getItem(0));
            }
            if (fixedCount != mViews.size()) {
                mViews.clear();
                mViews.add(null);
            }
        } else if (fixedCount > 1) {
            if (fixedCount + 2 != mList.size()) {
                mList.clear();
                // add last element in position 0, add all, add first element in last position
                mList.add(getItem(fixedCount - 1));
                for (int i = 0; i < fixedCount; i++) {
                    mList.add(getItem(i));
                }
                mList.add(getItem(0));
            }

            if (fixedCount + 2 != mViews.size()) {
                mViews.clear();
                for (int i = 0; i < mList.size(); i++) {
                    mViews.add(null);
                }
            }
        }
        super.notifyDataSetChanged();

        // this is very important
        mChildCount = getCount();

        if (mViewPager.getCurrentItem() == 0 && mChildCount != 1) {
            mViewPager.setCurrentItem(1, false);
        }

        stop();
        start();
    }

    public void setDelayMillis(final int delayMillis) {
        this.mDelayMillis = delayMillis;
        if (delayMillis <= 0) {
            mDelayMillis = DEFAULT_DELAY_MILLIS;
        }
    }

    /**
     * start loop
     */
    public void start() {
        if (!mRunning) {
            post();
            mRunning = true;
        }
    }

    /**
     * stop loop
     */
    public void stop() {
        if (mRunning) {
            mHandler.removeCallbacks(this);
            mRunning = false;
        }
    }

    @Override
    public final boolean onTouch(final View v,
                                 final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            stop();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            start();
        }
        return false;
    }

    @Override
    public final void run() {
        int currentPosition = mViewPager.getCurrentItem();
        if (0 < currentPosition && currentPosition < mList.size() - 1) {

            if (currentPosition + 1 == mList.size() - 1) {
                currentPosition = 1;
            } else {
                currentPosition++;
            }
            mViewPager.setCurrentItem(currentPosition, true);

            post();
        }
    }

    private void post() {
        mHandler.postDelayed(this, mDelayMillis);
    }

    @Override
    public final Object instantiateItem(final ViewGroup container,
                                        final int position) {
        int fixedPosition = 0;
        if (position == 0) {
            fixedPosition = getPagerCount() - 1;
        } else if (position == mList.size() - 1) {
            fixedPosition = 0;
        } else if (0 < position && position < mList.size() - 1) {
            fixedPosition = position - 1;
        }
        if (mViews.get(position) == null) {
            mViews.set(position, getView(fixedPosition, mViews.get(position), container));
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public final int getItemPosition(final Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public final void destroyItem(final ViewGroup container,
                                  final int position,
                                  final Object object) {
        container.removeView((View) object);
    }

    @Override
    public final int getCount() {
        return mList.size();
    }

    @Override
    public final boolean isViewFromObject(final View view,
                                          final Object object) {
        return view == object;
    }

    @Override
    public final void onPageScrolled(final int position,
                                     final float positionOffset,
                                     final int positionOffsetPixels) {
    }

    @Override
    public final void onPageSelected(final int position) {
        if (0 < position && position < mList.size() - 1) {
            onPageItemSelected(position - 1);
        }
    }

    @Override
    public final void onPageScrollStateChanged(final int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (mList.size() > 3) {
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(mList.size() - 2, false);
                } else if (mViewPager.getCurrentItem() == mList.size() - 1) {
                    mViewPager.setCurrentItem(1, false);
                }
            }
        }
    }
}
