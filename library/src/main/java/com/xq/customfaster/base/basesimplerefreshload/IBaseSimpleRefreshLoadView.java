package com.xq.customfaster.base.basesimplerefreshload;


import android.os.Bundle;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xq.customfaster.widget.view.RefreshLoadViewInterface;
import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.util.tools.ToastUtils;

public interface IBaseSimpleRefreshLoadView<T extends IBaseSimpleRefreshLoadPresenter> extends AbsView<T> {

    default void afterOnCreate(Bundle savedInstanceState) {

        if (getRootView() instanceof RefreshLoadViewInterface)
            getRefreshLoadBuilder().refreshView = (RefreshLoadViewInterface) getRootView();
        else
            getRefreshLoadBuilder().refreshView = (RefreshLoadViewInterface) findViewById(getContext().getResources().getIdentifier("refreshView", "id", getContext().getPackageName()));

        //以下初始化刷新控件
        if (getRefreshLoadBuilder().refreshView != null)
        {
            getRefreshLoadBuilder().refreshView.setRefreshLoadListener(new RefreshLoadViewInterface.OnRefreshLoadListener() {
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
            getRefreshLoadBuilder().refreshView.setHeaderView(getHeadView());
            getRefreshLoadBuilder().refreshView.setFootView(getFootView());
        }
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
    default void onSaveInstanceState(Bundle outState) {

    }

    //开始刷新，主要写给P层调用
    default void startRefresh(){
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.startRefresh();
        else
            refreshing();
    }

    //开始加载，主要写给P层调用
    default void startLoadmore(){
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.startLoadmore();
        else
            loadmoring();
    }

    //通知P层刷新，可以选择重写该方法，在刷新时传入更多参数
    default void refreshing() {
        getPresenter().refreshing();
    }

    //通知P层加载，可以选择重写该方法，在加载时传入更多参数
    default void loadmoring() {
        getPresenter().loadmoring();
    }

    //刷新完成后调用
    default void afterRefresh() {
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.finishRefreshing();
    }

    //加载完成后调用
    default void afterLoadmore() {
        if (getRefreshLoadBuilder().refreshView != null)
            getRefreshLoadBuilder().refreshView.finishLoadmore();
    }

    //刷新加载数据为空后处理
    default void afterEmpty(){
        if (getRefreshLoadBuilder().refreshView != null && getEmptyView() != null)
            getRefreshLoadBuilder().refreshView.setEmptyView(getEmptyView());
    }

    //刷新加载完毕后处理
    default void afterRefreshLoadEnd() {
        ToastUtils.showShort("已经到底了啦");
    }

    //刷新加载错误后处理
    default void afterRefreshLoadErro() {
        ToastUtils.showShort("没有数据哦");
    }

    //返回刷新布局
    default Object getHeadView(){
        return new ProgressLayout(getContext());
    }

    //返回加载布局
    default Object getFootView() {
        return new LoadingView(getContext());
    }

    //返回刷新加载的空布局方案
    default Object getEmptyView() {
        return null;
    }

    //在刷新时回调此方法，请重写此方法完成后续处理
    public void refreshView(Object object);

    //在加载时回调此方法，请重写此方法完成后续处理
    public void loadmoreView(Object object);

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder {
        public RefreshLoadViewInterface refreshView;
    }

}
