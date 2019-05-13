package com.xq.customfaster.base.basepager;

import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.worldbean.bean.behavior.FragmentBehavior;
import java.util.List;

public interface IAbsPagerView<T extends IAbsPagerPresenter> extends IAbsView<T> {

    //刷新ViewPage
    public void refreshPager();

    //提供给P层用于初始化
    public void initPager(List<FragmentBehavior> list);

}