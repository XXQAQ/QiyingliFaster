package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.customfaster.base.baserefreshload.IAbsRefreshLoadView;

import java.util.List;

public interface IAbsRefreshLoadListView<T extends IAbsRefreshLoadListPresenter> extends IAbsRefreshLoadView<T> {

    //初始化适配器，主要写给P层调用
    public void initAdapter(List list, Object... objects);

    //单Item刷新
    public void refreshItemView(int position);

    //单Item移除
    public void removeItemView(int position);

    //单Item新增
    public void insertItemView(int position);

}
