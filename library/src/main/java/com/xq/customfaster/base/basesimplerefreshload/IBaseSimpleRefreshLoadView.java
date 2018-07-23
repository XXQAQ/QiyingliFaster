package com.xq.customfaster.base.basesimplerefreshload;


import android.os.Bundle;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.xq.projectdefine.base.basesimplerefreshload.IFasterBaseSimpleRefreshLoadView;
import com.xq.projectdefine.util.tools.ToastUtils;


public interface IBaseSimpleRefreshLoadView<T extends IBaseSimpleRefreshLoadPresenter> extends IFasterBaseSimpleRefreshLoadView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IFasterBaseSimpleRefreshLoadView.super.afterOnCreate(savedInstanceState);
    }

    @Override
    default void onResume() {
        IFasterBaseSimpleRefreshLoadView.super.onResume();
    }

    @Override
    default void onPause() {
        IFasterBaseSimpleRefreshLoadView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IFasterBaseSimpleRefreshLoadView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IFasterBaseSimpleRefreshLoadView.super.onSaveInstanceState(outState);
    }

    @Override
    default void afterEmpty(){
        if (getRefreshLoadBuilder() != null && getEmptyView() != null)
            getRefreshLoadBuilder().refreshView.setEmptyView(getEmptyView());
    }

    @Override
    default void afterRefreshLoadEnd() {
        ToastUtils.showShort("已经到底了啦");
    }

    @Override
    default void afterRefreshLoadErro() {
        ToastUtils.showShort("没有数据哦");
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
    default Object getEmptyView() {
        return null;
    }

}
