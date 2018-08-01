package com.xq.customfaster.base.baseviewpager;


import com.xq.projectdefine.base.abs.AbsView;

public interface AbsViewPagerView<T extends AbsViewPagerPresenter> extends AbsView<T> {

    //刷新ViewPage
    public void refreshViewPager();

}