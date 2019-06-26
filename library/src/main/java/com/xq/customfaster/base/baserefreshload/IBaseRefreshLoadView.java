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

    @Override
    default boolean isRefresh() {
        return getRefreshLoadDelegate().isRefresh();
    }

    @Override
    default boolean isWorking() {
        return getRefreshLoadDelegate().isWorking();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadPresenter> extends AbsViewDelegate<T> implements IAbsRefreshLoadView<T>, RefreshLoadViewInterface.OnRefreshLoadListener {

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
                refreshLoadView.setOnRefreshLoadListener(this);
                if (getEmptyView() != null) refreshLoadView.setEmptyView(getEmptyView());
                if (getErroView() != null) refreshLoadView.setErroView(getErroView());
            }
        }

        @Override
        public void startRefresh(){
            if (refreshLoadView != null)
                refreshLoadView.startRefresh();
            else
                onRefresh(null);
        }

        @Override
        public void startLoadmore(){
            if (refreshLoadView != null)
                refreshLoadView.startLoadmore();
            else
                onLoadmore(null);
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

        @Override
        public boolean isRefresh() {
            return getBindPresenter().isRefresh();
        }

        @Override
        public boolean isWorking() {
            return getBindPresenter().isWorking();
        }

        //以下为刷新加载控件的监听器
        @Override
        public void onFinishRefresh(RefreshLoadViewInterface view) {

        }

        @Override
        public void onRefresh(RefreshLoadViewInterface view) {
            getBindPresenter().refresh();
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
            getBindPresenter().loadmore();
        }

        @Override
        public void onCancelLoadmore(RefreshLoadViewInterface view) {
            getBindPresenter().cancelLoadmore();
        }

        //获取空布局
        protected Object getEmptyView() {
            return null;
        }

        //获取错误布局
        protected Object getErroView() {
            return null;
        }

    }

}
