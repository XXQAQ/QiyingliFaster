package com.xq.customfaster.base.basesimplerefreshload;


import android.content.Intent;
import android.os.Bundle;
import com.xq.projectdefine.base.abs.AbsPresenter;


public interface IBaseSimpleRefreshLoadPresenter<T extends IBaseSimpleRefreshLoadView> extends AbsPresenter<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    default void onResume() {

    }

    @Override
    default void onPause() {

    }

    @Override
    default void onDestroy() {

    }

    @Override
    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    //开始刷新
    default void startRefresh(){
        getBindView().startRefresh();
    }

    //开始加载
    default void startLoadmore(){
        getBindView().startLoadmore();
    }

    //刷新中
    @Deprecated
    default void refreshing(Object... objects) {
        getRefreshLoadBuilder().isRefresh = true;
        refreshLoad(true, 1,objects);
    }

    //加载中
    @Deprecated
    default void loadmoring(Object... objects) {
        getRefreshLoadBuilder().isRefresh = false;
        refreshLoad(false, getRefreshLoadBuilder().page+1,objects);
    }

    //取消刷新
    default void cancleRefresh() {

    }

    //取消加载
    default void cancleLoadmore() {

    }

    //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
    public abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder {
        public int page = 1;
        public boolean isRefresh;
    }
}
