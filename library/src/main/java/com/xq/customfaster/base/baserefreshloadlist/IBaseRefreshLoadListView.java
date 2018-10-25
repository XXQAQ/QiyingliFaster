package com.xq.customfaster.base.baserefreshloadlist;


import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.customfaster.widget.view.RecyclerViewInterface;
import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.bean.behavior.ListBehavior;
import java.util.LinkedList;
import java.util.List;


public interface IBaseRefreshLoadListView<T extends IBaseRefreshLoadListPresenter> extends AbsRefreshLoadListView<T>, IBaseRefreshLoadView<T> {

    @Override
    default void initAdapter(List list, Object... objects) {
        getRefreshLoadDelegate().initAdapter(list,objects);
    }

    @Override
    default int getAdapterHeadCount() {
        return getRefreshLoadDelegate().getAdapterHeadCount();
    }

    @Override
    default int getAdapterFootCount() {
        return getRefreshLoadDelegate().getAdapterFootCount();
    }

    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadListPresenter> extends IBaseRefreshLoadView.RefreshLoadDelegate<T> implements AbsRefreshLoadListView<T> {

        public RecyclerView rv;

        public RefreshLoadDelegate(AbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            if (getRootView() instanceof RecyclerView)   //如果根布局是RecyclerView，则直接将根布局转化为rv
                rv = (RecyclerView) getRootView();
            else
                rv = (RecyclerView) getRootView().findViewById(getContext().getResources().getIdentifier("rv", "id", getContext().getPackageName()));

            //初始化RecyclerView
            rv.setLayoutManager(getLayoutManager());
            rv.setNestedScrollingEnabled(false);

            //通知P层初始化Adapter
            getPresenter().initAdapter();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
        }

        @Override
        public void initAdapter(List list, Object... objects) {
            rv.setAdapter(getAdapter(list,objects));
        }

        @Override
        public int getAdapterHeadCount() {
            if (rv instanceof RecyclerViewInterface)
                return ((RecyclerViewInterface) rv).getHeadCount();
            return 0;
        }

        @Override
        public int getAdapterFootCount() {
            if (rv instanceof RecyclerViewInterface)
                return ((RecyclerViewInterface) rv).getFootCount();
            return 0;
        }

        @Override
        public void refreshView(Object object){

            List updateList = null;
            if (object instanceof List)
                updateList = (List) object;
            else    if (object instanceof ListBehavior)
                updateList = ((ListBehavior) object).getList();
            else
                return;

            if (updateList == null)
                updateList = new LinkedList();

            //DiffUtil在数据未变动的的时候不会调用adapter任何方法，因此需要手动调用notifyDataSetChanged
            if (updateList.size() > 0)
            {
                List newList = new LinkedList();
                newList.addAll(updateList);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefaultDiffCallBack(getPresenter().getListData(),newList),true);
                diffResult.dispatchUpdatesTo(rv.getAdapter());
                getPresenter().getListData().clear();
                getPresenter().getListData().addAll(updateList);
            }
            else    rv.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void loadmoreView(Object object){

            List updateList = null;
            if (object instanceof List)
                updateList = (List) object;
            else    if (object instanceof ListBehavior)
                updateList = ((ListBehavior) object).getList();
            else
                return;

            if (updateList == null)
                updateList = new LinkedList();
            
            //DiffUtil在数据未变动的的时候不会调用adapter任何方法，因此需要手动调用notifyDataSetChanged
            if (updateList.size() > 0)
            {
                List newList = new LinkedList();
                newList.addAll(getPresenter().getListData());
                newList.addAll(updateList);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefaultDiffCallBack(getPresenter().getListData(), newList), true);
                diffResult.dispatchUpdatesTo(rv.getAdapter());
                getPresenter().getListData().addAll(updateList);
            }
            else    rv.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void refreshLoadEmpty(){
            if (rv instanceof RecyclerViewInterface)
            {
                View view = (View) getEmptyView();
                if (view == null)
                    view = new View(getContext());
                ((RecyclerViewInterface) rv).setEmptyView(view);
            }
        }

        //返回适配器，可以在重写时为Adater设置更多参数
        protected abstract RecyclerView.Adapter getAdapter(List list, Object... objects);

        //返回布局管理器，重写该方法以指定RecyclerView的布局方案
        protected abstract RecyclerView.LayoutManager getLayoutManager();

        protected class DefaultDiffCallBack extends DiffUtil.Callback {

            private List mOldDatas, mNewDatas;

            public DefaultDiffCallBack(List mOldDatas, List mNewDatas) {
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
                return mNewDatas.get(newItemPosition).getClass().isAssignableFrom(mOldDatas.get(oldItemPosition).getClass());
            }
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
            }
        }

    }

}
