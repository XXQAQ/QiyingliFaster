package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.customfaster.base.baserefreshload.IAbsRefreshLoadPresenter;
import java.util.List;

public interface IAbsRefreshLoadListPresenter<T extends IAbsRefreshLoadListView> extends IAbsRefreshLoadPresenter<T>, IAbsRefreshLoadListCommon {

    //获取数据列表
    public List getDataList();

    //单Item刷新(全新ItemBean替换)
    public void refreshItem(int position, Object object);

    //单Item刷新(ItemBean在List中已存在)
    public void refreshItem(Object object);

    //单Item刷新
    public void refreshItem(int position);

    //单Item移除(ItemBean在List中已存在)
    public void removeItem(Object object);

    //单Item移除
    public void removeItem(int position);

    //单Item新增
    public void insertItem(int position, Object object);

}
