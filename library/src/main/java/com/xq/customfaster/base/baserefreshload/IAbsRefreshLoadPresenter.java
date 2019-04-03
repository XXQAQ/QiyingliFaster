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

    //刷新加载数据回调的方法(如果不需要经过本地或网络处理，请直接调用本方法)
    public void refreshLoadData();

    //刷新加载数据回调的方法
    public void refreshLoadData(Object object);

    //刷新加载数据回调的方法
    public void refreshLoadData(Object object,boolean isSuccess);

    //获取刷新(true)和加载(false)的状态
    public boolean isRefresh();

    //是否刷新中或加载中
    public boolean isWorking();

}
