package com.xq.customfaster.base.basesimplerefreshload;


import com.xq.projectdefine.base.abs.AbsPresenter;

public interface AbsSimpleRefreshLoadPresenter<T extends AbsSimpleRefreshLoadView> extends AbsPresenter<T> {

    //开始刷新
    public void startRefresh();

    //开始加载
    public void startLoadmore();

    //刷新中
    @Deprecated
    public void refreshing(Object... objects);

    //加载中
    @Deprecated
    public void loadmoring(Object... objects);

    //取消刷新
    public void cancleRefresh();

    //取消加载
    public void cancleLoadmore();

}
