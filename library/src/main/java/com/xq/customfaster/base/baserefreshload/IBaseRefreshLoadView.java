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
    default void refreshEmpty() {
        getRefreshLoadDelegate().refreshEmpty();
    }

    @Override
    default void loadmoreEmpty(){
        getRefreshLoadDelegate().loadmoreEmpty();
    }

    @Override
    default void refreshErro() {
        getRefreshLoadDelegate().refreshErro();
    }

    @Override
    default void loadmoreErro() {
        getRefreshLoadDelegate().loadmoreErro();
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
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            refreshLoadView = (RefreshLoadViewInterface) findViewById(getContext().getResources().getIdentifier("refreshLoadView", "id", getContext().getPackageName()));

            //以下初始化刷新控件
            if (refreshLoadView != null)
            {
                refreshLoadView.setOnRefreshLoadListener(new RefreshLoadViewInterface.OnRefreshLoadListener() {
                    @Override
                    public void onFinishRefresh(RefreshLoadViewInterface view) {

                    }

                    @Override
                    public void onRefresh(RefreshLoadViewInterface view) {
                        refreshing();
                    }

                    @Override
                    public void onCancelRefresh(RefreshLoadViewInterface view) {
                        getBindPresenter().cancelRefresh();
                    }

                    @Override
                    public void onFinishLoadmore(RefreshLoadViewInterface view) {

                    }

                    @Override
                    public void onLoadmore(RefreshLoadViewInterface view) {
                        loadmoring();
                    }

                    @Override
                    public void onCancelLoadmore(RefreshLoadViewInterface view) {
                        getBindPresenter().cancelLoadmore();
                    }
                });
                refreshLoadView.setRefreshHeaderView(getRefreshHeadView());
                refreshLoadView.setLoadmoreFooterView(getLoadmoreFootView());
                refreshLoadView.setEmptyView(getEmptyView());
                refreshLoadView.setErroView(getErroView());
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
            getBindPresenter().refreshing();
        }

        @Override
        public void loadmoring() {
            getBindPresenter().loadmoring();
        }

        @Override
        public void refreshView(Object object){
            if (refreshLoadView != null) refreshLoadView.showContent();
        }

        @Override
        public void loadmoreView(Object object) {

        }

        @Override
        public void refreshEmpty() {
            if (refreshLoadView != null) refreshLoadView.showEmpty();
        }

        @Override
        public void loadmoreEmpty() {
            ToastUtils.showShort("没有更多数据啦");
        }

        @Override
        public void refreshErro() {
            if (refreshLoadView != null) refreshLoadView.showErro();
        }

        @Override
        public void loadmoreErro() {
            ToastUtils.showShort("数据加载失败");
        }

        @Override
        public void afterRefresh() {
            if (refreshLoadView != null) refreshLoadView.finishRefresh();
        }

        @Override
        public void afterLoadmore() {
            if (refreshLoadView != null) refreshLoadView.finishLoadmore();
        }

        //获取刷新头布局
        protected Object getRefreshHeadView(){
            return new ProgressLayout(getContext());
        }

        //获取加载尾布局
        protected Object getLoadmoreFootView() {
            return new LoadingView(getContext());
        }

        //获取空布局
        protected Object getEmptyView() {
            return new View(getContext());
        }

        //获取错误布局
        protected Object getErroView() {
            return new View(getContext());
        }

    }

}
