package com.xq.customfaster.base.basesimplerefreshload;


import com.xq.projectdefine.base.abs.AbsView;

public interface AbsSimpleRefreshLoadView<T extends AbsSimpleRefreshLoadPresenter> extends AbsView<T> {

    //开始刷新，主要写给P层调用
    public void startRefresh();

    //开始加载，主要写给P层调用
    public void startLoadmore();

    //通知P层刷新，可以选择重写该方法，在刷新时传入更多参数
    public void refreshing();

    //通知P层加载，可以选择重写该方法，在加载时传入更多参数
    public void loadmoring();

    //刷新完成后调用
    public void afterRefresh();

    //加载完成后调用
    public void afterLoadmore();

    //刷新加载数据为空后处理
    public void afterEmpty();

    //刷新加载完毕后处理
    public void afterRefreshLoadEnd();

    //刷新加载错误后处理
    public void afterRefreshLoadErro();

    //在刷新时回调此方法，请重写此方法完成后续处理
    public abstract void refreshView(Object object);

    //在加载时回调此方法，请重写此方法完成后续处理
    public abstract void loadmoreView(Object object);

}
