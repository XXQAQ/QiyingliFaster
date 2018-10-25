package com.xq.customfaster.base.baserefreshloadlist;


import com.xq.customfaster.base.baserefreshload.AbsRefreshLoadView;

import java.util.List;

public interface AbsRefreshLoadListView<T extends AbsRefreshLoadListPresenter> extends AbsRefreshLoadView<T> {

    //初始化适配器，主要写给P层调用
    public void initAdapter(List list, Object... objects);

}
