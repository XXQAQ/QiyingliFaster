package com.xq.customfaster.widget.view;

public interface RefreshLoadViewInterface {

    public boolean isFirstRefresh();

    public void setIsFirstRefresh(boolean isFirstRefresh);

    //设置是否纯净越界模式
    public void setPureScrollModeOn(boolean isPureScrollMode);

    //设置头布局
    public void setRefreshHeaderView(Object object);

    //设置尾布局
    public void setLoadmoreFooterView(Object object);

    //开始刷新
    public void startRefresh();

    //开始加载
    public void startLoadmore();

    //结束刷新
    public void finishRefresh();

    //结束加载
    public void finishLoadmore();

    //设置可否刷新
    public void setEnableRefresh(boolean enable);

    //设置可否加载
    public void setEnableLoadmore(boolean enable);

    //设置状态监听
    public void setOnRefreshLoadListener(OnRefreshLoadListener listener);

    //设置加载布局
    public void setLoadingView(Object object);

    //显示加载中页面
    public void showLoading();

    //显示内容布局
    public void showContent();

    //设置空布局
    public void setEmptyView(Object object);

    //显示空布局
    public void showEmpty();

    //设置错误布局
    public void setErroView(Object object);

    //显示错误布局
    public void showErro();

    public static interface OnRefreshLoadListener {

        public void onRefresh();

        public void onLoadmore();

    }

}
