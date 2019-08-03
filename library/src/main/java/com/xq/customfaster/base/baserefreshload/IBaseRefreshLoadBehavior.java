package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.core.Controler;

public interface IBaseRefreshLoadBehavior extends Controler {

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    //刷新中
    public void refresh(Object... objects);

    //加载中
    public void loadmore(Object... objects);

    //取消刷新
    public void cancelRefresh();

    //取消加载
    public void cancelLoadmore();

    //刷新加载数据回调的方法
    public void refreshLoadData(Object object);

    //刷新加载数据回调的方法
    public void refreshLoadData(Object object, boolean isSuccess);

    //获取刷新(true)和加载(false)的状态
    public boolean isRefresh();

    //是否刷新中或加载中
    public boolean isWorking();



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    //开始刷新
    public void startRefresh();

    //开始加载
    public void startLoadmore();

    //在刷新时回调此方法，请重写此方法完成后续处理
    public abstract void refreshView(Object object);

    //在加载时回调此方法，请重写此方法完成后续处理
    public abstract void loadmoreView(Object object);

    //刷新数据为空后处理
    public void refreshEmpty();

    //加载数据为空后处理
    public void loadmoreEmpty();

    //刷新数据错误后处理
    public void refreshErro();

    //加载数据错误后处理
    public void loadmoreErro();

    //刷新完成后调用
    public void afterRefresh();

    //加载完成后调用
    public void afterLoadmore();

}
