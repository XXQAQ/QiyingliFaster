package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadBehavior;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;
import java.util.List;

public interface IBaseRefreshLoadListBehavior extends IBaseRefreshLoadBehavior {

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    //获取数据
    public Pointer<ListBehavior> getPointer();

    //获取数据列表
    public List getDataList();

    //单Item刷新
    public void refreshItem(Object object);

    //单Item刷新
    public void refreshItem(int position);

    //单Item移除
    public void removeItem(Object object);

    //单Item移除
    public void removeItem(int position);

    //单Item新增
    public void insertItem(int position, Object object);



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    //获取role列表
    public List<String> getRoleList();

    //初始化适配器，主要写给P层调用
    public void initAdapter(Pointer<ListBehavior> pointer, Object... objects);

    //刷新适配器
    public void refreshAdapter();

}
