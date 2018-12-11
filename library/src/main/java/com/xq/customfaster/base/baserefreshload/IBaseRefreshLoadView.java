package com.xq.customfaster.base.baserefreshload;

import android.os.Bundle;
import android.view.View;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.util.tools.ToastUtils;
import com.xq.customfaster.widget.view.RefreshLoadViewInterface;

public interface IBaseRefreshLoadView<T extends IBaseRefreshLoadPresenter> extends IAbsRefreshLoadView<T> {

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
    default void refreshView(Object object) {
        getRefreshLoadDelegate().refreshView(object);
    }

    @Override
    default void loadmoreView(Object object) {
        getRefreshLoadDelegate().loadmoreView(object);
    }

    @Override
    default void refreshLoadEmpty(){
        getRefreshLoadDelegate().refreshLoadEmpty();
    }

    @Override
    default void refreshLoadErro() {
        getRefreshLoadDelegate().refreshLoadErro();
    }

    @Override
    default void afterRefresh() {
        getRefreshLoadDelegate().afterRefresh();
    }

    @Override
    default void afterLoadmore() {
        getRefreshLoadDelegate().afterLoadmore();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadPresenter> extends AbsViewDelegate<T> implements IAbsRefreshLoadView<T> {

        public RefreshLoadViewInterface refreshLoadView;

        public RefreshLoadDelegate(IAbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            refreshLoadView = (RefreshLoadViewInterface) findViewById(getContext().getResources().getIdentifier("refreshLoadView", "id", getContext().getPackageName()));

            //以下初始化刷新控件
            if (refreshLoadView != null)
            {
                refreshLoadView.setRefreshLoadListener(new RefreshLoadViewInterface.OnRefreshLoadListener() {
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
                refreshLoadView.setRefreshHeadView(getRefreshHeadView());
                refreshLoadView.setLoadmoreFootView(getLoadmoreFootView());
            }
        }

        @Override
        public void startRefresh(){
            if (refreshLoadView != null)
                refreshLoadView.startRefresh();
            else
                refreshing();
        }

        @Override
        public void startLoadmore(){
            if (refreshLoadView != null)
                refreshLoadView.startLoadmore();
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
        public void refreshLoadEmpty(){
            if (refreshLoadView != null)
            {
                View view = (View) getEmptyView();
                if (view == null)
                    view = new View(getContext());
                refreshLoadView.setEmptyView(view);
                refreshLoadView.showEmptyView();
            }
        }

        @Override
        public void refreshLoadErro() {
            ToastUtils.showShort("数据加载失败");
        }

        @Override
        public void afterRefresh() {
            if (refreshLoadView != null)
                refreshLoadView.finishRefreshing();
        }

        @Override
        public void afterLoadmore() {
            if (refreshLoadView != null)
                refreshLoadView.finishLoadmore();
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
