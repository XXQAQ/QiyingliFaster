package com.xq.customfaster.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

public class CustomRefreshLoadView extends TwinklingRefreshLayout implements RefreshLoadViewInterface {

    public CustomRefreshLoadView(Context context) {
        super(context);
    }

    public CustomRefreshLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRefreshLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void setRefreshHeaderView(Object o) {
        super.setHeaderView((IHeaderView) o);
    }

    @Override
    public void setLoadmoreFooterView(Object o) {
        super.setBottomView((IBottomView) o);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void setRefreshLoadListener(OnRefreshLoadListener onRefreshLoadListener) {
        super.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefreshCanceled() {
                super.onRefreshCanceled();
                onRefreshLoadListener.onCancleRefresh(CustomRefreshLoadView.this);
            }

            @Override
            public void onLoadmoreCanceled() {
                super.onLoadmoreCanceled();
                onRefreshLoadListener.onCancleLoadmore(CustomRefreshLoadView.this);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                onRefreshLoadListener.onRefresh(CustomRefreshLoadView.this);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                onRefreshLoadListener.onLoadmore(CustomRefreshLoadView.this);
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
                onRefreshLoadListener.onFinishRefresh(CustomRefreshLoadView.this);
            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
                onRefreshLoadListener.onFinishLoadmore(CustomRefreshLoadView.this);
            }
        });
    }
}
