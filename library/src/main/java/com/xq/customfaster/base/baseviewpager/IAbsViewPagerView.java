package com.xq.customfaster.base.baseviewpager;

import com.xq.androidfaster.base.abs.IAbsView;

public interface IAbsViewPagerView<T extends IAbsViewPagerPresenter> extends IAbsView<T> {

    //刷新ViewPage
    public void refreshViewPager();

}