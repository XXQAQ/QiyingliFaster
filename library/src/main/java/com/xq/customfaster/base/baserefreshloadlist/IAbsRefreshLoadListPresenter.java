package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.customfaster.base.baserefreshload.IAbsRefreshLoadPresenter;

import java.util.List;

public interface IAbsRefreshLoadListPresenter<T extends IAbsRefreshLoadListView> extends IAbsRefreshLoadPresenter<T> {

    //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
    public void initAdapter();

    //获取数据列表
    public List getDataList();

}
