package com.xq.customfaster.base.baserefreshload;

import com.xq.androidfaster.base.abs.IAbsCommon;

public interface IAbsRefreshLoadCommon extends IAbsCommon {

    //开始刷新
    public void startRefresh();

    //开始加载
    public void startLoadmore();

    //获取刷新(true)和加载(false)的状态
    public boolean isRefresh();

    //是否刷新中或加载中
    public boolean isWorking();

}
