package com.xq.customfaster.base.basesimplerefreshload;


import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xq.customfaster.widget.view.RefreshLoadViewInterface;
import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.base.abs.AbsViewDelegate;
import com.xq.projectdefine.util.tools.ToastUtils;

public interface IBaseSimpleRefreshLoadView<T extends AbsSimpleRefreshLoadPresenter> extends AbsSimpleRefreshLoadView<T> {

    @Override
    default void startRefresh(){
        getRefreshLoadDelegate().startRefresh();
    }

    @Override
    default void startLoadmore(){
        getRefreshLoadDelegate().startLoadmore();
    }

    @Override
    default void refreshing() {
        getRefreshLoadDelegate().refreshing();
    }

    @Override
    default void loadmoring() {
        getRefreshLoadDelegate().loadmoring();
    }

    @Override
    default void afterRefresh() {
        getRefreshLoadDelegate().afterRefresh();
    }

    @Override
    default void afterLoadmore() {
        getRefreshLoadDelegate().afterLoadmore();
    }

    @Override
    default void afterEmpty(){
        getRefreshLoadDelegate().afterEmpty();
    }

    @Override
    default void afterRefreshLoadEnd() {
        getRefreshLoadDelegate().afterRefreshLoadEnd();
    }

    @Override
    default void afterRefreshLoadErro() {
        getRefreshLoadDelegate().afterRefreshLoadErro();
    }

    @Override
    default void refreshView(Object object) {
        getRefreshLoadDelegate().refreshView(object);
    }

    @Override
    default void loadmoreView(Object object) {
        getRefreshLoadDelegate().loadmoreView(object);
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends AbsSimpleRefreshLoadPresenter> extends AbsViewDelegate<T> implements AbsSimpleRefreshLoadView<T> {

        public RefreshLoadViewInterface refreshView;

        public RefreshLoadDelegate(AbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {

            if (getRootView() instanceof RefreshLoadViewInterface)
                refreshView = (RefreshLoadViewInterface) getRootView();
            else
                refreshView = (RefreshLoadViewInterface) findViewById(getContext().getResources().getIdentifier("refreshView", "id", getContext().getPackageName()));

            //以下初始化刷新控件
            if (refreshView != null)
            {
                refreshView.setRefreshLoadListener(new RefreshLoadViewInterface.OnRefreshLoadListener() {
                    @Override
                    public void onFinishRefresh(RefreshLoadViewInterface view) {

                    }

                    @Override
                    public void onRefresh(RefreshLoadViewInterface view) {
                        refreshing();
                    }

                    @Override
                    public void onCancleRefresh(RefreshLoadViewInterface view) {
                        getPresenter().cancleRefresh();
                    }

                    @Override
                    public void onFinishLoadmore(RefreshLoadViewInterface view) {

                    }

                    @Override
                    public void onLoadmore(RefreshLoadViewInterface view) {
                        loadmoring();
                    }

                    @Override
                    public void onCancleLoadmore(RefreshLoadViewInterface view) {
                        getPresenter().cancleLoadmore();
                    }
                });
                refreshView.setRefreshHeadView(getRefreshHeadView());
                refreshView.setLoadmoreFootView(getLoadmoreFootView());
            }
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
        public void onSaveInstanceState(Bundle bundle) {

        }

        @Override
        public void startRefresh(){
            if (refreshView != null)
                refreshView.startRefresh();
            else
                refreshing();
        }

        @Override
        public void startLoadmore(){
            if (refreshView != null)
                refreshView.startLoadmore();
            else
                loadmoring();
        }

        @Override
        public void refreshing() {
            getPresenter().refreshing();
        }

        @Override
        public void loadmoring() {
            getPresenter().loadmoring();
        }

        @Override
        public void afterRefresh() {
            if (refreshView != null)
                refreshView.finishRefreshing();
        }

        @Override
        public void afterLoadmore() {
            if (refreshView != null)
                refreshView.finishLoadmore();
        }

        @Override
        public void afterEmpty(){
            if (refreshView != null)
            {
                View view = (View) getEmptyView();
                if (view == null)
                    view = new View(getContext());
                refreshView.setEmptyView(view);
                refreshView.showEmptyView();
            }
        }

        @Override
        public void afterRefreshLoadEnd() {
            ToastUtils.showShort("没有数据了哦");
        }

        @Override
        public void afterRefreshLoadErro() {
            ToastUtils.showShort("数据加载失败");
        }

        //返回刷新头布局
        protected Object getRefreshHeadView(){
            return new ProgressLayout(getContext());
        }

        //返回加载尾布局
        protected Object getLoadmoreFootView() {
            return new LoadingView(getContext());
        }

        //返回空布局
        protected Object getEmptyView() {
            return null;
        }

    }

}
