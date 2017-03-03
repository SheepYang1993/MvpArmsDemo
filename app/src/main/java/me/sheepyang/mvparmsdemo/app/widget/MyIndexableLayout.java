package me.sheepyang.mvparmsdemo.app.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import me.yokeyword.indexablerv.IndexableLayout;

/**
 * Created by SheepYang on 2017/3/1.
 */

public class MyIndexableLayout extends IndexableLayout {
    public MyIndexableLayout(Context context) {
        this(context, null);
    }

    public MyIndexableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyIndexableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (((LinearLayoutManager) getRecyclerView().getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0) {
            if (ev.getX() > (getMeasuredWidth() - getChildAt(2).getMeasuredWidth())) {
                getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
