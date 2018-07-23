package com.xq.customfaster.base.basesimplerefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadPresenter;


public interface IBaseSimpleRefreshLoadPresenter<T extends IBaseSimpleRefreshLoadView> extends IFasterBaseSimpleRefreshLoadPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseSimpleRefreshLoadPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseSimpleRefreshLoadPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseSimpleRefreshLoadPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseSimpleRefreshLoadPresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFasterBaseSimpleRefreshLoadPresenter.super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    default void cancleRefresh(){

    }

    @Override
    default void cancleLoadmore(){

    }
}
