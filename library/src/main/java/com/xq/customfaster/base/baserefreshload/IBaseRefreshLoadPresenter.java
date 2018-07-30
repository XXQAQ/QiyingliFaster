package com.xq.customfaster.base.baserefreshload;


import android.content.Intent;
import android.os.Bundle;
import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadPresenter;
import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadPresenter<T extends IBaseRefreshLoadView> extends IBaseSimpleRefreshLoadPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IBaseSimpleRefreshLoadPresenter.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IBaseSimpleRefreshLoadPresenter.super.onResume();
    }

    @Override
    default void onPause() {
        IBaseSimpleRefreshLoadPresenter.super.onPause();
    }

    @Override
    default void onDestroy() {
        IBaseSimpleRefreshLoadPresenter.super.onDestroy();
    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {
        IBaseSimpleRefreshLoadPresenter.super.onActivityResult(requestCode,resultCode,data);
    }

    //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
    default void initAdapter(){
        getBindView().initAdapter(getRefreshLoadBuilder().list_data);
    }

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder extends IBaseSimpleRefreshLoadPresenter.RefreshLoadBuilder{
        public List list_data = new LinkedList<>();
    }

}
