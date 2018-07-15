package com.xq.customfaster.base.baseviewpager;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.baseviewpager.IFasterBaseViewPagerPresenter;


public interface IBaseViewPagerPresenter<T extends IBaseViewPagerView> extends IFasterBaseViewPagerPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseViewPagerPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseViewPagerPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseViewPagerPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseViewPagerPresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFasterBaseViewPagerPresenter.super.onActivityResult(requestCode,resultCode,data);
    }

}