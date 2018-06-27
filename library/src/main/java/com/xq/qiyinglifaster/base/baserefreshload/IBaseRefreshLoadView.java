package com.xq.qiyinglifaster.base.baserefreshload;


import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xq.projectdefine.base.baserefreshload.IFasterBaseRefreshLoadView;
import com.xq.projectdefine.util.DefaultToast;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;


public interface IBaseRefreshLoadView<T extends IBaseRefreshLoadPresenter> extends IFasterBaseRefreshLoadView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseRefreshLoadView.super.afterOnCreate(savedInstanceState);

        getRefreshLoadBuilder().rv.setNestedScrollingEnabled(false);
    }

    @Override
    default void onResume() {
        IFasterBaseRefreshLoadView.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseRefreshLoadView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseRefreshLoadView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IFasterBaseRefreshLoadView.super.onSaveInstanceState(outState);
    }

    default void afterRefreshLoadEnd() {
        DefaultToast.showToast(getContext(),"没有更多数据啦");
    }

    default void afterRefreshLoadErro(){
        DefaultToast.showToast(getContext(),"加载失败,请稍后重试");
    }

    default int getHeadViewCount() {
        if (getRefreshLoadBuilder().rv instanceof FamiliarRecyclerView)
        {
            FamiliarRecyclerView familiarRecyclerView = (FamiliarRecyclerView) getRefreshLoadBuilder().rv;
            return familiarRecyclerView.getHeaderViewsCount();
        }
        return 0;
    }

    default int getFoodViewCount() {
        if (getRefreshLoadBuilder().rv instanceof FamiliarRecyclerView)
        {
            FamiliarRecyclerView familiarRecyclerView = (FamiliarRecyclerView) getRefreshLoadBuilder().rv;
            return familiarRecyclerView.getFooterViewsCount();
        }
        return 0;
    }

    default void afterEmpty(){
        if (getRefreshLoadBuilder().rv instanceof FamiliarRecyclerView && ((FamiliarRecyclerView)getRefreshLoadBuilder().rv).getEmptyView() == null)     //如果是FamiliarRecyclerView，且没有空布局，则设置空布局
        {
            FamiliarRecyclerView familiarRecyclerView = (FamiliarRecyclerView) getRefreshLoadBuilder().rv;
            View view = getEmptyView();
            if (view == null)
                view = new View(getContext());
            familiarRecyclerView.setEmptyView(view,true);
        }
    }

    @Override
    default void showRefreshLoadEnd() {
        DefaultToast.showToast(getContext(),"已经到底了啦");
    }

    @Override
    default void showRefreshLoadErro() {
        DefaultToast.showToast(getContext(),"没有数据哦");
    }

    @Override
    default Object getHeadView(){
        return new ProgressLayout(getContext());
    }

    @Override
    default Object getFootView() {
        return new LoadingView(getContext());
    }

    @Override
    default View getEmptyView() {
        return null;
    }

}
