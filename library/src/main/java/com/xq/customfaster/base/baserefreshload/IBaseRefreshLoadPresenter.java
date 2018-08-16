package com.xq.customfaster.base.baserefreshload;


import android.content.Intent;
import android.os.Bundle;

import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.base.abs.AbsPresenterDelegate;
import com.xq.projectdefine.util.tools.ObjectUtils;

public interface IBaseRefreshLoadPresenter<T extends IBaseRefreshLoadView> extends AbsRefreshLoadPresenter<T> {

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

    @Override
    default void refreshLoadData(boolean isOperateSuccess,Object object) {
        getRefreshLoadDelegate().refreshLoadData(isOperateSuccess,object);
    }

    @Override
    default void refreshLoadErro() {
        getRefreshLoadDelegate().refreshLoadErro();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadView> extends AbsPresenterDelegate<T> implements AbsRefreshLoadPresenter<T> {

        protected int page = getFirstPage();
        protected boolean isRefresh;
        protected boolean isWorking;

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
            if (isWorking)
                return;

            isWorking = true;
            isRefresh = true;
            refreshLoad(true, getFirstPage(),objects);
        }

        @Override
        public void loadmoring(Object... objects) {
            if (isWorking)
                return;

            isWorking = true;
            isRefresh = false;
            refreshLoad(false, page + 1,objects);
        }

        @Override
        public void cancleRefresh() {

        }

        @Override
        public void cancleLoadmore() {

        }

        @Override
        public void refreshLoadData(boolean isOperateSuccess,Object object) {

            isWorking = false;

            if (isEmptyData(isOperateSuccess,object))
                getBindView().refreshLoadEmpty();

            if (isRefresh)
            {
                if (!isEmptyData(isOperateSuccess,object))
                    page = getFirstPage();
                getBindView().refreshView(object);
                getBindView().afterRefresh();
            }
            else
            {
                if (!isEmptyData(isOperateSuccess,object))
                    page++;
                getBindView().loadmoreView(object);
                getBindView().afterLoadmore();
            }

        }

        @Override
        public void refreshLoadErro() {
            getBindView().refreshLoadErro();
        }

        //判断是对象否含有数据(您需要根据需要重写该方法，因为page和emptyView需要本方法的返回值进行后续处理)
        protected boolean isEmptyData(boolean isOperateSuccess,Object object){
            if (isOperateSuccess)
                return ObjectUtils.isEmpty(object);
            else
                return false;
        }

        //重写此方法可指定第一页下标
        protected int getFirstPage(){
            return 1;
        }

        //屏蔽了刷新和加载的差异，提供给程序员以实现刷新或加载的方法
        protected abstract void refreshLoad(boolean isRefresh, int page, Object... objects);

    }
}
