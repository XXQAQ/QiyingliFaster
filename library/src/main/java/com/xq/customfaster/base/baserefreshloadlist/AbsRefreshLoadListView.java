package com.xq.customfaster.base.baserefreshloadlist;


import com.xq.customfaster.base.baserefreshload.AbsRefreshLoadView;

import java.util.List;

public interface AbsRefreshLoadListView<T extends AbsRefreshLoadListPresenter> extends AbsRefreshLoadView<T> {

    //初始化适配器，主要写给P层调用
    public void initAdapter(List list, Object... objects);

    //返回头布局数量，防止adapter item总数异常
    public int getAdapterHeadCount();

    //返回尾布局数量，防止adapter item总数异常
    public int getAdapterFootCount();

}
