package com.xq.customfaster.base.baserefreshload;


import com.xq.customfaster.base.basesimplerefreshload.AbsSimpleRefreshLoadView;

import java.util.List;

public interface AbsRefreshLoadView<T extends AbsRefreshLoadPresenter> extends AbsSimpleRefreshLoadView<T> {

    //初始化适配器，主要写给P层调用
    public void initAdapter(List list, Object... objects);

    //返回头布局数量，防止adapter item总数异常
    public int getAdapterHeadCount();

    //返回尾布局数量，防止adapter item总数异常
    public int getAdapterFootCount();

}
