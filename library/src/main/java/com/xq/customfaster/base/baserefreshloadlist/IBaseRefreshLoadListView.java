package com.xq.customfaster.base.baserefreshloadlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.base.core.Controler;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadView;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IBaseRefreshLoadListView extends IBaseRefreshLoadView, IBaseRefreshLoadListBehavior {

    public RefreshLoadDelegate getRefreshLoadDelegate();

    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default List<String> getRoleList() {
        return getRefreshLoadDelegate().getRoleList();
    }

    @Override
    default void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
        getRefreshLoadDelegate().initAdapter(pointer,objects);
    }

    @Override
    default void refreshAdapter() {
        getRefreshLoadDelegate().refreshAdapter();
    }



    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default Pointer<ListBehavior> getPointer() {
        return getRefreshLoadDelegate().getPointer();
    }

    @Deprecated
    @Override
    default List getDataList() {
        return getRefreshLoadDelegate().getDataList();
    }

    @Deprecated
    @Override
    default void refreshItem(Object object) {
        getRefreshLoadDelegate().refreshItem(object);
    }

    @Deprecated
    @Override
    default void refreshItem(int position) {
        getRefreshLoadDelegate().refreshItem(position);
    }

    @Deprecated
    @Override
    default void removeItem(Object object) {
        getRefreshLoadDelegate().removeItem(object);
    }

    @Deprecated
    @Override
    default void removeItem(int position) {
        getRefreshLoadDelegate().removeItem(position);
    }

    @Deprecated
    @Override
    default void insertItem(int position, Object object) {
        getRefreshLoadDelegate().insertItem(position,object);
    }

    public abstract class RefreshLoadDelegate extends IBaseRefreshLoadView.RefreshLoadDelegate implements IBaseRefreshLoadListBehavior {

        public RecyclerView recyclerView;

        private Map<String,RecyclerView.Adapter> map_childAdapter = new LinkedHashMap<>();

        public RefreshLoadDelegate(Controler controler) {
            super(controler);
        }

        @Override
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            recyclerView = (RecyclerView) findViewById(getContext().getResources().getIdentifier("recyclerView", "id", getContext().getPackageName()));

            //初始化RecyclerView
            recyclerView.setLayoutManager(getLayoutManager());
        }

        @Override
        public List<String> getRoleList() {
            ArrayList<String> list = new ArrayList<>();
            list.addAll(map_childAdapter.keySet());
            return list;
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
            super.refreshView(object);
            refreshAdapter();
        }

        @Override
        public void loadmoreView(Object object){
            super.loadmoreView(object);
            refreshAdapter();
        }

        @Override
        public void refreshEmpty() {
            super.refreshEmpty();
            refreshAdapter();
        }

        @Override
        public void loadmoreEmpty() {
            super.loadmoreEmpty();
            refreshAdapter();
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



        ///////////////////////////////////////////////////////////////////////////
        // P
        ///////////////////////////////////////////////////////////////////////////
        @Override
        public Pointer<ListBehavior> getPointer() {
            return ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).getPointer();
        }

        @Deprecated
        @Override
        public List getDataList() {
            return ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).getDataList();
        }

        @Deprecated
        @Override
        public void refreshItem(Object object) {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshItem(object);
        }

        @Deprecated
        @Override
        public void refreshItem(int position) {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshItem(position);
        }

        @Deprecated
        @Override
        public void removeItem(Object object) {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshItem(object);
        }

        @Deprecated
        @Override
        public void removeItem(int position) {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshItem(position);
        }

        @Deprecated
        @Override
        public void insertItem(int position, Object object) {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).insertItem(position,object);
        }
    }
}
