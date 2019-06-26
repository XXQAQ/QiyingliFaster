package com.xq.customfaster.base.baserefreshloadlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IBaseRefreshLoadListView<T extends IBaseRefreshLoadListPresenter> extends IAbsRefreshLoadListView<T>, IBaseRefreshLoadView<T> {

    @Override
    default void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
        getRefreshLoadDelegate().initAdapter(pointer,objects);
    }

    @Override
    default void refreshAdapter() {
        getRefreshLoadDelegate().refreshAdapter();
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
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            recyclerView = (RecyclerView) findViewById(getContext().getResources().getIdentifier("recyclerView", "id", getContext().getPackageName()));

            //初始化RecyclerView
            recyclerView.setLayoutManager(getLayoutManager());
        }

        @Override
        public void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
            recyclerView.setAdapter(getAdapter(pointer,objects));
        }

        @Override
        public void refreshAdapter() {
            if (getRoleList().isEmpty())
            {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            else
            {
                for (Map.Entry<String,RecyclerView.Adapter> entry : map_childAdapter.entrySet())
                    entry.getValue().notifyDataSetChanged();
            }
        }

        @Override
        public void refreshView(Object object){
            refreshAdapter();
        }

        @Override
        public void loadmoreView(Object object){
            refreshAdapter();
        }

        @Override
        public void refreshEmpty() {
            refreshAdapter();
        }

        @Override
        public void loadmoreEmpty() {
            refreshAdapter();
        }

        @Override
        public List<String> getRoleList() {
            ArrayList<String> list = new ArrayList<>();
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
