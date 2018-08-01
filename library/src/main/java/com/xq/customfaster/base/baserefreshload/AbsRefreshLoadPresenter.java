package com.xq.customfaster.base.baserefreshload;


import com.xq.customfaster.base.basesimplerefreshload.AbsSimpleRefreshLoadPresenter;

import java.util.List;

public interface AbsRefreshLoadPresenter<T extends AbsRefreshLoadView> extends AbsSimpleRefreshLoadPresenter<T> {

    //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
    public void initAdapter();

    //获取列表数据
    public List getListData();

}
