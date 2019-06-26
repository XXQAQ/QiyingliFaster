package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.customfaster.base.baserefreshload.IAbsRefreshLoadView;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;

public interface IAbsRefreshLoadListView<T extends IAbsRefreshLoadListPresenter> extends IAbsRefreshLoadView<T>, IAbsRefreshLoadListCommon {

    //初始化适配器，主要写给P层调用
    public void initAdapter(Pointer<ListBehavior> pointer, Object... objects);

    public void refreshAdapter();

}
