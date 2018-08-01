package com.xq.customfaster.base.basesimplerefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsPresenterDelegate;

public interface IBaseSimpleRefreshLoadPresenter<T extends AbsSimpleRefreshLoadView> extends AbsSimpleRefreshLoadPresenter<T> {

    @Override
    default void startRefresh(){
        getRefreshLoadBuilder().startRefresh();
    }

    @Override
    default void startLoadmore(){
        getRefreshLoadBuilder().startLoadmore();
    }

    @Override
    default void refreshing(Object... objects) {
        getRefreshLoadBuilder().refreshing(objects);
    }

    @Override
    default void loadmoring(Object... objects) {
        getRefreshLoadBuilder().loadmoring(objects);
    }

    @Override
    default void cancleRefresh() {
        getRefreshLoadBuilder().cancleRefresh();
    }

    @Override
    default void cancleLoadmore() {
        getRefreshLoadBuilder().cancleLoadmore();
    }

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public abstract class RefreshLoadBuilder<T extends AbsSimpleRefreshLoadView> extends AbsPresenterDelegate<T> implements AbsSimpleRefreshLoadPresenter<T> {

        public int page = 1;
        public boolean isRefresh;

        public RefreshLoadBuilder(AbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void afterOnCreate(Bundle bundle) {

        }

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onActivityResult(int i, int i1, Intent intent) {

        }

        @Override
        public void startRefresh(){
            getBindView().startRefresh();
        }

        @Override
        public void startLoadmore(){
            getBindView().startLoadmore();
        }

        @Override
        public void refreshing(Object... objects) {
            isRefresh = true;
            refreshLoad(true, 1,objects);
        }

        @Override
        public void loadmoring(Object... objects) {
            isRefresh = false;
            refreshLoad(false, page+1,objects);
        }

        @Override
        public void cancleRefresh() {

        }

        @Override
        public void cancleLoadmore() {

        }

        //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
        protected abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    }
}
