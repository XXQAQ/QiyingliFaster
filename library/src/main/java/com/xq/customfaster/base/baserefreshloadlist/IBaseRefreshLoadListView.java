package com.xq.customfaster.base.baserefreshloadlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IBaseRefreshLoadListView<T extends IBaseRefreshLoadListPresenter> extends IAbsRefreshLoadListView<T>, IBaseRefreshLoadView<T> {

    @Override
    default void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
        getRefreshLoadDelegate().initAdapter(pointer,objects);
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
    default List<String> getRoleList() {
        return getRefreshLoadDelegate().getRoleList();
    }

    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadListPresenter> extends IBaseRefreshLoadView.RefreshLoadDelegate<T> implements IAbsRefreshLoadListView<T> {

        public RecyclerView recyclerView;

        private Map<String,RecyclerView.Adapter> map_childAdapter = new LinkedHashMap<>();

        public RefreshLoadDelegate(IAbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            recyclerView = (RecyclerView) findViewById(getContext().getResources().getIdentifier("recyclerView", "id", getContext().getPackageName()));

            //初始化RecyclerView
            recyclerView.setLayoutManager(getLayoutManager());

        }

        @Override
        public void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
            recyclerView.setAdapter(getAdapter(pointer,objects));
        }

        @Override
        public void refreshView(Object object){
            if (getRoleList().isEmpty())
                recyclerView.getAdapter().notifyDataSetChanged();
            else
            {
                for (Map.Entry<String,RecyclerView.Adapter> entry : map_childAdapter.entrySet())
                    entry.getValue().notifyDataSetChanged();
            }
        }

        @Override
        public void loadmoreView(Object object){
            if (getRoleList().isEmpty())
                recyclerView.getAdapter().notifyDataSetChanged();
            else
            {
                Iterator<Map.Entry<String, RecyclerView.Adapter>> iterator = map_childAdapter.entrySet().iterator();
                Map.Entry<String, RecyclerView.Adapter> tail = null;
                while (iterator.hasNext()) tail = iterator.next();
                tail.getValue().notifyDataSetChanged();
            }
        }

        @Override
        public void refreshEmpty() {
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void refreshItemView(int position) {
            recyclerView.getAdapter().notifyItemChanged(position);
        }

        @Override
        public void removeItemView(int position) {
            recyclerView.getAdapter().notifyItemRemoved(position);
        }

        @Override
        public void insertItemView(int position) {
            recyclerView.getAdapter().notifyItemInserted(position);
        }

        @Override
        public List<String> getRoleList() {
            ArrayList list = new ArrayList();
            list.addAll(map_childAdapter.keySet());
            return list;
        }

        //填充子Adapter
        protected void putChildAdapter(String role,RecyclerView.Adapter adapter){
            map_childAdapter.put(role,adapter);
        }

        //获取子Adapter
        protected RecyclerView.Adapter getChildAdapter(String role){
            return map_childAdapter.get(role);
        }

        //返回适配器，可以在重写时为Adater设置更多参数
        protected abstract RecyclerView.Adapter getAdapter(Pointer<ListBehavior> pointer, Object... objects);

        //返回布局管理器，重写该方法以指定RecyclerView的布局方案
        protected abstract RecyclerView.LayoutManager getLayoutManager();

    }
}
