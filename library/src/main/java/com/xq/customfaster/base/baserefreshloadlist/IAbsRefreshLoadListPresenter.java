package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.customfaster.base.baserefreshload.IAbsRefreshLoadPresenter;

import java.util.List;

public interface IAbsRefreshLoadListPresenter<T extends IAbsRefreshLoadListView> extends IAbsRefreshLoadPresenter<T> {

    //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
    public void initAdapter();

    //新增数据
    public void addDatas(List list);

    //新增数据(自带去重)
    public void addDifferentDatas(List list);

    //清空数据
    public void clearDatas();

    //单Item刷新(全新ItemBean替换)
    public void refreshItem(int position, Object object);

    //单Item刷新(ItemBean在List中已存在)
    public void refreshItem(Object object);

    //单Item刷新(ItemBean在List中已存在)
    public void refreshItem(int position);

    //单Item移除
    public void removeItem(Object object);

    //单Item移除
    public void removeItem(int position);

    //单Item新增
    public void insertItem(int position, Object object);

    //获取数据列表
    public List getDataList();

}
