package com.xq.customfaster.base.baserefreshloadlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.bean.behavior.ListBehavior;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.customfaster.widget.view.RecyclerViewInterface;
import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadListView<T extends IBaseRefreshLoadListPresenter> extends IAbsRefreshLoadListView<T>, IBaseRefreshLoadView<T> {

    @Override
    default void initAdapter(List list, Object... objects) {
        getRefreshLoadDelegate().initAdapter(list,objects);
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
            getPresenter().initAdapter();
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

            getPresenter().getDataList().clear();
            getPresenter().getDataList().addAll(updateList);
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

            getPresenter().getDataList().addAll(updateList);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void refreshLoadEmpty(){
            if (recyclerView instanceof RecyclerViewInterface)
            {
                View view = (View) getEmptyView();
                if (view == null) view = new View(getContext());
                ((RecyclerViewInterface) recyclerView).setEmptyView(view);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        //返回适配器，可以在重写时为Adater设置更多参数
        protected abstract RecyclerView.Adapter getAdapter(List list, Object... objects);

        //返回布局管理器，重写该方法以指定RecyclerView的布局方案
        protected abstract RecyclerView.LayoutManager getLayoutManager();

    }
}
