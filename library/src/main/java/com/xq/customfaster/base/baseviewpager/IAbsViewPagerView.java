package com.xq.customfaster.base.baseviewpager;

import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.bean.behavior.FragmentTitleBehavior;
import java.util.List;

public interface IAbsViewPagerView<T extends IAbsViewPagerPresenter> extends IAbsView<T> {

    //刷新ViewPage
    public void refreshViewPager();

    //提供给P层用于初始化ViewPager
    public void initViewPager(List<FragmentTitleBehavior> list);

}