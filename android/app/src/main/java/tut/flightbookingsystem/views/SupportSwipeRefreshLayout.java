package tut.flightbookingsystem.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Let's say that the RecyclerView contains an EditText at the middle of screen. We start application
 * (topRowVerticalPosition = 0), taps on the EditText. As result, software keyboard shows up, size of
 * the RecyclerView is decreased, it is automatically scrolled by system to keep the EditText
 * visible and topRowVerticalPosition should not be 0, but onScrolled is not called and
 * topRowVerticalPosition is not recalculated.
 *
 * From: http://stackoverflow.com/questions/25178329/recyclerview-and-swiperefreshlayout
 */

public class SupportSwipeRefreshLayout extends SwipeRefreshLayout {
    private RecyclerView mInternalRecyclerView = null;

    public SupportSwipeRefreshLayout(Context context) {
        super(context);
        isInEditMode();
    }

    public SupportSwipeRefreshLayout(Context context,
                                     AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInternalRecyclerView(RecyclerView internalRecyclerView) {
        mInternalRecyclerView = internalRecyclerView;
        isInEditMode();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isInEditMode();
        if (mInternalRecyclerView.canScrollVertically(-1)) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
