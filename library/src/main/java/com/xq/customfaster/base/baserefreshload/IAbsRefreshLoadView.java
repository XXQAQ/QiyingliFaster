package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.abs.IAbsView;

public interface IAbsRefreshLoadView<T extends IAbsRefreshLoadPresenter> extends IAbsView<T>, IAbsRefreshLoadCommon {

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
