package com.xq.customfaster.base.baserefreshloadlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.util.tools.ToastUtils;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.customfaster.widget.view.RecyclerViewInterface;
import com.xq.worldbean.bean.behavior.ListBehavior;
import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadListView<T extends IBaseRefreshLoadListPresenter> extends IAbsRefreshLoadListView<T>, IBaseRefreshLoadView<T> {

    @Override
    default void initAdapter(List list, Object... objects) {
        getRefreshLoadDelegate().initAdapter(list,objects);
    }

    @Override
    default void refreshItemView(int position) {
        getRefreshLoadDelegate().refreshItemView(position);
    }

    @Override
    default void removeItemView(int position) {
        getRefreshLoadDelegate().removeItemView(position);
    }

    @Override
    default void insertItemView(int position) {
        getRefreshLoadDelegate().insertItemView(position);
    }

    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadListPresenter> extends IBaseRefreshLoadView.RefreshLoadDelegate<T> implements IAbsRefreshLoadListView<T> {

        public RecyclerView recyclerView;

        public RefreshLoadDelegate(IAbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            recyclerView = (RecyclerView) findViewById(getContext().getResources().getIdentifier("recyclerView", "id", getContext().getPackageName()));

            //初始化RecyclerView
            recyclerView.setLayoutManager(getLayoutManager());
            recyclerView.setNestedScrollingEnabled(false);

            //通知P层初始化Adapter
            getBindPresenter().initAdapter();
        }

        @Override
        public void initAdapter(List list, Object... objects) {
            recyclerView.setAdapter(getAdapter(list,objects));
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

            getBindPresenter().clearDatas();
            getBindPresenter().addDifferentDatas(updateList);
            recyclerView.getAdapter().notifyDataSetChanged();
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

            getBindPresenter().addDifferentDatas(updateList);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void refreshEmpty() {
            if (recyclerView instanceof RecyclerViewInterface)
            {
                ((RecyclerViewInterface) recyclerView).setEmptyView(getEmptyView());
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void loadmoreEmpty() {
            ToastUtils.showShort("没有更多数据啦");
        }

        @Override
        public void refreshErro() {
            if (recyclerView instanceof RecyclerViewInterface)
            {
                ((RecyclerViewInterface) recyclerView).setEmptyView(getErroView());
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public void loadmoreErro() {
            ToastUtils.showShort("数据加载失败");
        }

        @Override
        public void refreshItemView(int position) {
            int headerCount = 0;
            if (recyclerView instanceof RecyclerViewInterface) headerCount = ((RecyclerViewInterface) recyclerView).getHeaderCount();
            recyclerView.getAdapter().notifyItemChanged(position+headerCount);
        }

        @Override
        public void removeItemView(int position) {
            int headerCount = 0;
            if (recyclerView instanceof RecyclerViewInterface) headerCount = ((RecyclerViewInterface) recyclerView).getHeaderCount();
            recyclerView.getAdapter().notifyItemRemoved(position+headerCount);
        }

        @Override
        public void insertItemView(int position) {
            int headerCount = 0;
            if (recyclerView instanceof RecyclerViewInterface) headerCount = ((RecyclerViewInterface) recyclerView).getHeaderCount();
            recyclerView.getAdapter().notifyItemInserted(position+headerCount);
        }

        //返回适配器，可以在重写时为Adater设置更多参数
        protected abstract RecyclerView.Adapter getAdapter(List list, Object... objects);

        //返回布局管理器，重写该方法以指定RecyclerView的布局方案
        protected abstract RecyclerView.LayoutManager getLayoutManager();

    }
}
