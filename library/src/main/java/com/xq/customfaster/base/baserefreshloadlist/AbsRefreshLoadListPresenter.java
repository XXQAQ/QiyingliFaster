package com.xq.customfaster.base.baserefreshloadlist;


import com.xq.customfaster.base.baserefreshload.AbsRefreshLoadPresenter;

import java.util.List;

public interface AbsRefreshLoadListPresenter<T extends AbsRefreshLoadListView> extends AbsRefreshLoadPresenter<T> {

    //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
    public void initAdapter();

    //获取列表数据
    public List getListData();

}
