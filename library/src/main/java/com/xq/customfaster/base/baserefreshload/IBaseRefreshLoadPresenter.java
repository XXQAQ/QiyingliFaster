package com.xq.customfaster.base.baserefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadPresenter;


public interface IBaseRefreshLoadPresenter<T extends IBaseRefreshLoadView> extends IFasterBaseRefreshLoadPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseRefreshLoadPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseRefreshLoadPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseRefreshLoadPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseRefreshLoadPresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFasterBaseRefreshLoadPresenter.super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    default void cancleRefresh(){

    }

    @Override
    default void cancleLoadmore(){

    }
}
