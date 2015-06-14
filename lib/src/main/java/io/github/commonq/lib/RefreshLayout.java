package io.github.commonq.lib;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

public class RefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    private View mListViewFooter;
    private SwipyRefreshLayoutDirection direction;


    private int mYDown;
    private int mLastY;

    private boolean isLoading = false;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setDirection(SwipyRefreshLayoutDirection direction){
        this.direction=direction;
        if(this.direction==SwipyRefreshLayoutDirection.BOTTOM||this.direction==SwipyRefreshLayoutDirection.NONE){
            this.setEnabled(false);

        }else{
            this.setEnabled(true);
        }
    }

    //set the footer of the ListView with a ProgressBar in it
    public void setFooterView(Context context, ListView _ListView, int layoutId) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(layoutId, null,
                false);
        _ListView.addFooterView(mListViewFooter);
        _ListView.setFooterDividersEnabled(false);
        this.mListView = _ListView;
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int lastItemIndex;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(direction==SwipyRefreshLayoutDirection.BOTH||direction==SwipyRefreshLayoutDirection.BOTTOM) {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && lastItemIndex == mListView.getAdapter().getCount() - 2) {
                        loadData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemIndex = firstVisibleItem + visibleItemCount - 1 -1;
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                if (isPullingUp())
                    //you can add view or hint here when pulling up the ListView
                break;

            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && isPullingUp()&&(direction==SwipyRefreshLayoutDirection.BOTTOM||direction==SwipyRefreshLayoutDirection.BOTH);
    }

    private boolean isBottom() {
        if (mListView.getCount() > 0) {
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            if (isRefreshing()) setRefreshing(false);
            if (mListView.getFooterViewsCount() == 0) {
                mListView.addFooterView(mListViewFooter);
                mListView.setSelection(mListView.getAdapter().getCount() - 1);
            } else {
                mListViewFooter.setVisibility(VISIBLE);
                //mListView.addFooterView(mListViewFooter);
            }
        } else {
            if (mListView.getAdapter() instanceof HeaderViewListAdapter) {
                mListView.removeFooterView(mListViewFooter);
            } else {
                mListViewFooter.setVisibility(View.GONE);
            }
            mYDown = 0;
            mLastY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }


}