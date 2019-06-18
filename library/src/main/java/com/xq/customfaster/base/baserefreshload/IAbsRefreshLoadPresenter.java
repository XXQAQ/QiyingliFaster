package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.abs.IAbsPresenter;

public interface IAbsRefreshLoadPresenter<T extends IAbsRefreshLoadView> extends IAbsPresenter<T>, IAbsRefreshLoadCommon {

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

}
