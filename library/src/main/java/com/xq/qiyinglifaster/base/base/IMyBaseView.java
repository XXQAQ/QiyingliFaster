package com.xq.qiyinglifaster.base.base;


import android.os.Bundle;
import com.xq.projectdefine.base.base.IFasterBaseView;

public interface IMyBaseView<T extends IMyBasePresenter> extends IFasterBaseView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseView.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseView.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IFasterBaseView.super.onSaveInstanceState(outState);
    }

    //手动调用以设置Toolbar标题
    public void initToolbar(String title);

    //手动调用以设置Toolbar标题+图标
    public void initToolbar(String title, boolean isShowIcon);

    //页面行为描述
    public String getBehaviorDescription();

}
