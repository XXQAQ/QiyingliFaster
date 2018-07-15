package com.xq.customfaster.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.xq.projectdefine.base.basesimplerefreshload.RefreshLoadCustomView;


public class CustomTwinklingRefreshView extends TwinklingRefreshLayout implements RefreshLoadCustomView {

    public CustomTwinklingRefreshView(Context context) {
        super(context);
    }

    public CustomTwinklingRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTwinklingRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void startLoadmore() {

    }

    @Override
    public void startRefresh() {
        super.startRefresh();
    }

    @Override
    public void finishLoadmore() {
        super.finishLoadmore();
    }

    @Override
    public void finishRefreshing() {
        super.finishRefreshing();
    }

    @Override
    public void setEmptyView(Object o) {

    }

    @Override
    public void setHeaderView(Object o) {
        super.setHeaderView((IHeaderView) o);
    }

    @Override
    public void setFootView(Object o) {
        super.setBottomView((IBottomView) o);
    }

    @Override
    public void setRefreshLoadListener(OnRefreshLoadListener onRefreshLoadListener) {
        super.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefreshCanceled() {
                super.onRefreshCanceled();
                onRefreshLoadListener.onCancleRefresh(CustomTwinklingRefreshView.this);
            }

            @Override
            public void onLoadmoreCanceled() {
                super.onLoadmoreCanceled();
                onRefreshLoadListener.onCancleLoadmore(CustomTwinklingRefreshView.this);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                onRefreshLoadListener.onRefresh(CustomTwinklingRefreshView.this);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                onRefreshLoadListener.onLoadmore(CustomTwinklingRefreshView.this);
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
                onRefreshLoadListener.onFinishRefresh(CustomTwinklingRefreshView.this);
            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
                onRefreshLoadListener.onFinishLoadmore(CustomTwinklingRefreshView.this);
            }
        });
    }
}
