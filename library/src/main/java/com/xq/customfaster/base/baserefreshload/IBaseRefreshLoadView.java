package com.xq.customfaster.base.baserefreshload;


import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;


public interface IBaseRefreshLoadView<T extends IBaseRefreshLoadPresenter> extends IBaseSimpleRefreshLoadView<T> {

    @Override
    default void afterOnCreate(Bundle savedInstanceState) {
        IBaseSimpleRefreshLoadView.super.afterOnCreate(savedInstanceState);

        if (getRootView() instanceof RecyclerView)   //如果根布局是RecyclerView，则直接将根布局转化为rv
            getRefreshLoadBuilder().rv = (RecyclerView) getRootView();
        else
            getRefreshLoadBuilder().rv = (RecyclerView) getRootView().findViewById(getContext().getResources().getIdentifier("rv", "id", getContext().getPackageName()));

        //初始化RecyclerView
        getRefreshLoadBuilder().rv.setLayoutManager(getLayoutManager());
        getRefreshLoadBuilder().rv.setNestedScrollingEnabled(false);

        //通知P层初始化Adapter
        getPresenter().initAdapter();
    }

    @Override
    default void onResume() {
        IBaseSimpleRefreshLoadView.super.onResume();
    }

    @Override
    default void onPause() {
        IBaseSimpleRefreshLoadView.super.onPause();
    }

    @Override
    default void onDestroy() {
        IBaseSimpleRefreshLoadView.super.onDestroy();
    }

    @Override
    default void onSaveInstanceState(Bundle outState) {
        IBaseSimpleRefreshLoadView.super.onSaveInstanceState(outState);
    }

    //初始化适配器，主要写给P层调用
    default void initAdapter(List list, Object... objects) {
        getRefreshLoadBuilder().rv.setAdapter(getAdapter(list,objects));
    }

    //返回适配器，可以选择重写该方法，为Adater设置更多参数
    public RecyclerView.Adapter getAdapter(List list, Object... objects);

    //返回布局管理器，可以选择重写该方法以指定RecyclerView的布局方案
    public RecyclerView.LayoutManager getLayoutManager();

    //返回头布局数量，防止adapter item总数异常
    default int getAdapterHeadCount() {
        if (getRefreshLoadBuilder().rv instanceof FamiliarRecyclerView)
        {
            FamiliarRecyclerView familiarRecyclerView = (FamiliarRecyclerView) getRefreshLoadBuilder().rv;
            return familiarRecyclerView.getHeaderViewsCount();
        }
        return 0;
    }

    //返回尾布局数量，防止adapter item总数异常
    default int getAdapterFootCount() {
        if (getRefreshLoadBuilder().rv instanceof FamiliarRecyclerView)
        {
            FamiliarRecyclerView familiarRecyclerView = (FamiliarRecyclerView) getRefreshLoadBuilder().rv;
            return familiarRecyclerView.getFooterViewsCount();
        }
        return 0;
    }

    @Override
    default void refreshView(Object object){
        if (object == null)
            return;
        if (object instanceof List)
        {
            List updateList = (List) object;
            List newList = updateList;
            RecyclerView.Adapter adapter = getRefreshLoadBuilder().rv.getAdapter();
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(getPresenter().getRefreshLoadBuilder().list_data,newList),false);
            diffResult.dispatchUpdatesTo(adapter);
            getPresenter().getRefreshLoadBuilder().list_data.clear();
            getPresenter().getRefreshLoadBuilder().list_data.addAll(updateList);
        }
    }

    @Override
    default void loadmoreView(Object object){
        if (object == null)
            return;
        if (object instanceof List)
        {
            List updateList = (List) object;
            List newList = new LinkedList();
            newList.addAll(getPresenter().getRefreshLoadBuilder().list_data);
            newList.addAll((Collection) updateList);
            RecyclerView.Adapter adapter = getRefreshLoadBuilder().rv.getAdapter();
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(getPresenter().getRefreshLoadBuilder().list_data, newList), false);
            diffResult.dispatchUpdatesTo(adapter);
            getPresenter().getRefreshLoadBuilder().list_data.addAll(updateList);
        }
    }

    @Override
    default void afterEmpty(){
        if (getRefreshLoadBuilder().rv instanceof FamiliarRecyclerView && ((FamiliarRecyclerView)getRefreshLoadBuilder().rv).getEmptyView() == null)     //如果是FamiliarRecyclerView，且没有空布局，则设置空布局
        {
            FamiliarRecyclerView familiarRecyclerView = (FamiliarRecyclerView) getRefreshLoadBuilder().rv;
            View view = (View) getEmptyView();
            if (view == null)
                view = new View(getContext());
            familiarRecyclerView.setEmptyView(view,true);
        }
    }

    public RefreshLoadBuilder getRefreshLoadBuilder();

    public static class RefreshLoadBuilder extends IBaseSimpleRefreshLoadView.RefreshLoadBuilder{
        public RecyclerView rv;
    }

    public static class DiffCallBack extends DiffUtil.Callback {
        private List mOldDatas, mNewDatas;

        public DiffCallBack(List mOldDatas, List mNewDatas) {
            this.mOldDatas = mOldDatas;
            this.mNewDatas = mNewDatas;
        }

        @Override
        public int getOldListSize() {
            return mOldDatas != null ? mOldDatas.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return mNewDatas != null ? mNewDatas.size() : 0;
        }
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        }
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        }
    }

}
