package com.xq.qiyinglifaster.base.base;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.base.IFasterBasePresenter;

public interface IMyBasePresenter<T extends IMyBaseView> extends IFasterBasePresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBasePresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBasePresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBasePresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBasePresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFasterBasePresenter.super.onActivityResult(requestCode,resultCode,data);
    }

}
