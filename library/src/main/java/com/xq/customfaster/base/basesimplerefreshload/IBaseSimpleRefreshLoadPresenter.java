package com.xq.customfaster.base.basesimplerefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.basesimplerefreshload.IFasterSimpleBaseRefreshLoadPresenter;


public interface IBaseSimpleRefreshLoadPresenter<T extends IBaseSimpleRefreshLoadView> extends IFasterSimpleBaseRefreshLoadPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterSimpleBaseRefreshLoadPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterSimpleBaseRefreshLoadPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterSimpleBaseRefreshLoadPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterSimpleBaseRefreshLoadPresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFasterSimpleBaseRefreshLoadPresenter.super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    default void cancleRefresh(){

    }

    @Override
    default void cancleLoadmore(){

    }
}
