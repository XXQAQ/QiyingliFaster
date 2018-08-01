package com.xq.customfaster.base.basesimplerefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsPresenterDelegate;

public interface IBaseSimpleRefreshLoadPresenter<T extends AbsSimpleRefreshLoadView> extends AbsSimpleRefreshLoadPresenter<T> {

    @Override
    default void startRefresh(){
        getRefreshLoadDelegate().startRefresh();
    }

    @Override
    default void startLoadmore(){
        getRefreshLoadDelegate().startLoadmore();
    }

    @Override
    default void refreshing(Object... objects) {
        getRefreshLoadDelegate().refreshing(objects);
    }

    @Override
    default void loadmoring(Object... objects) {
        getRefreshLoadDelegate().loadmoring(objects);
    }

    @Override
    default void cancleRefresh() {
        getRefreshLoadDelegate().cancleRefresh();
    }

    @Override
    default void cancleLoadmore() {
        getRefreshLoadDelegate().cancleLoadmore();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends AbsSimpleRefreshLoadView> extends AbsPresenterDelegate<T> implements AbsSimpleRefreshLoadPresenter<T> {

        public int page = getFirstPage();
        public boolean isRefresh;

        public RefreshLoadDelegate(AbsPresenter presenter) {
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
            refreshLoad(true, getFirstPage(),objects);
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

        //重写此方法可指定第一页下标
        public int getFirstPage(){
            return 1;
        }

        //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
        protected abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    }
}
