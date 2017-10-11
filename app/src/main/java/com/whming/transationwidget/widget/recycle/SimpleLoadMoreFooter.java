package com.whming.transationwidget.widget.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.whming.transationwidget.R;

/**
 * Created by squirrel on 2017/10/10.
 */

class SimpleLoadMoreFooter extends BaseFooter {

    private View view;
    public SimpleLoadMoreFooter(Context mContext, RecyclerView mRecyclerView) {
        super();
        view = LayoutInflater.from(mContext).inflate(R.layout.widget_footer,mRecyclerView,false);
    }

    @Override
    public View getContainerView() {

        return view;
    }

    @Override
    protected void setNoMoreDataView() {

    }

    @Override
    protected void setHasMoreDataView() {

    }

    @Override
    protected void setLoadMoreErrorView() {

    }

    @Override
    public void hideLoadMoreView() {

    }

    @Override
    public void showLoadMoreView() {

    }
}
