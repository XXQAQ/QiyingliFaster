package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.abs.IAbsPresenter;

public interface IAbsRefreshLoadPresenter<T extends IAbsRefreshLoadView> extends IAbsPresenter<T> {

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

    //刷新加载数据回调的方法
    public void refreshLoadData(boolean isOperateSuccess, Object object);

    //刷新加载失败后回调的方法
    public void refreshLoadErro();

}
