package com.xq.customfaster.widget.view;

public interface RefreshLoadViewInterface {

    //开始刷新
    public void startRefresh();

    //开始加载
    public void startLoadmore();

    //结束加载
    public void finishLoadmore();

    //结束刷新
    public void finishRefreshing();

    //设置空布局
    public void setEmptyView(Object object);

    //设置头布局
    public void setRefreshHeaderView(Object object);

    //设置尾布局
    public void setLoadmoreFooterView(Object object);

    //显示空布局
    public void showEmptyView();

    //设置可否刷新
    public void setEnableRefresh(boolean enable);

    //设置可否加载
    public void setEnableLoadmore(boolean enable);

    //设置状态监听
    public void setRefreshLoadListener(OnRefreshLoadListener listeneri);

    public static interface OnRefreshLoadListener {

        public void onFinishRefresh(RefreshLoadViewInterface view);

        public void onRefresh(RefreshLoadViewInterface view);

        public void onCancleRefresh(RefreshLoadViewInterface view);

        public void onFinishLoadmore(RefreshLoadViewInterface view);

        public void onLoadmore(RefreshLoadViewInterface view);

        public void onCancleLoadmore(RefreshLoadViewInterface view);

    }

}
