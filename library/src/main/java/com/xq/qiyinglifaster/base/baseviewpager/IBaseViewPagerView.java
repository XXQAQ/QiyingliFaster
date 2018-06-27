package com.xq.qiyinglifaster.base.baseviewpager;


import android.os.Bundle;

import com.xq.projectdefine.base.baseviewpager.IFasterBaseViewPagerView;

/**
 * Created by xq on 2017/4/11 0011.
 */

public interface IBaseViewPagerView<T extends IBaseViewPagerPresenter> extends IFasterBaseViewPagerView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseViewPagerView.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseViewPagerView.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseViewPagerView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseViewPagerView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IFasterBaseViewPagerView.super.onSaveInstanceState(outState);
    }

    default int getTabTextNormalColor() {
        return -1;
    }

    default int getTabTextSelectColor() {
        return -1;
    }

    default int getTabBackgroundColor() {
        return -1;
    }

}