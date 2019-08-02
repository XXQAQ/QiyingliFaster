package com.xq.customfaster.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

public class CustomRefreshLoadView extends TwinklingRefreshLayout implements RefreshLoadViewInterface {

    public CustomRefreshLoadView(Context context) {
        super(context);
        init();
    }

    public CustomRefreshLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRefreshLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOverScrollHeight(64);
        setPureScrollModeOn(true);
        setRefreshHeaderView(new ProgressLayout(getContext()));
        setLoadmoreFooterView( new LoadingView(getContext()));
    }

    private boolean isFirstRefresh = true;
    @Override
    public boolean isFirstRefresh() {
        return isFirstRefresh;
    }

    @Override
    public void setIsFirstRefresh(boolean isFirstRefresh) {
        this.isFirstRefresh = isFirstRefresh;
    }

    @Override
    public void setPureScrollModeOn(boolean isPureScrollMode){
        isPureScrollModeOn = isPureScrollMode;
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
    public void startRefresh() {
        super.startRefresh();
    }

    @Override
    public void startLoadmore() {
        super.startLoadMore();
    }

    @Override
    public void finishRefresh() {
        super.finishRefreshing();
    }

    @Override
    public void finishLoadmore() {
        super.finishLoadmore();
    }

    @Override
    public void setEnableRefresh(boolean enableRefresh1) {
        super.setEnableRefresh(enableRefresh1);
    }

    @Override
    public void setEnableLoadmore(boolean enableLoadmore1) {
        super.setEnableLoadmore(enableLoadmore1);
    }

    @Override
    public void setOnRefreshLoadListener(OnRefreshLoadListener onRefreshLoadListener) {
        super.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefreshCanceled() {
                super.onRefreshCanceled();
            }

            @Override
            public void onLoadmoreCanceled() {
                super.onLoadmoreCanceled();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                onRefreshLoadListener.onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                onRefreshLoadListener.onLoadmore();
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
            }
        });
    }

    @Override
    public void setLoadingView(Object object) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void setEmptyView(Object o) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void setErroView(Object o) {

    }

    @Override
    public void showErro() {

    }

}
