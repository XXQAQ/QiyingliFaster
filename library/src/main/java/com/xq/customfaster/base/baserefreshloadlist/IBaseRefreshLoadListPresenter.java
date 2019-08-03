package com.xq.customfaster.base.baserefreshloadlist;

import android.os.Bundle;
import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.base.core.Controler;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import com.xq.worldbean.bean.behavior.IdBehavior;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;
import java.util.List;

public interface IBaseRefreshLoadListPresenter extends IBaseRefreshLoadPresenter, IBaseRefreshLoadListBehavior{

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default List getDataList() {
        return getRefreshLoadDelegate().getDataList();
    }

    @Override
    default void refreshItem(Object object) {
        getRefreshLoadDelegate().refreshItem(object);
    }

    @Override
    default void refreshItem(int position) {
        getRefreshLoadDelegate().refreshItem(position);
    }

    @Override
    default void removeItem(Object object) {
        getRefreshLoadDelegate().removeItem(object);
    }

    @Override
    default void removeItem(int position) {
        getRefreshLoadDelegate().removeItem(position);
    }

    @Override
    default void insertItem(int position, Object object) {
        getRefreshLoadDelegate().insertItem(position,object);
    }



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    @Deprecated
    @Override
    default List<String> getRoleList() {
        return getRefreshLoadDelegate().getRoleList();
    }

    @Deprecated
    @Override
    default void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
        getRefreshLoadDelegate().initAdapter(pointer,objects);
    }

    @Deprecated
    @Override
    default void refreshAdapter() {
        getRefreshLoadDelegate().refreshAdapter();
    }

    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate extends IBaseRefreshLoadPresenter.RefreshLoadDelegate implements IBaseRefreshLoadListBehavior {

        protected Pointer<ListBehavior> pointer = new Pointer<>();

        public RefreshLoadDelegate(Controler controler) {
            super(controler);
        }

        @Override
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            initAdapter();
        }

        @Override
        public void refreshItem(Object object) {
            for (int i=0;i<getDataList().size();i++)
            {
                if (isEquals(object,getDataList().get(i)))
                {
                    getDataList().remove(i);
                    getDataList().add(i, object);
                    refreshItem(i);
                }
            }
        }

        @Override
        public void refreshItem(int position) {
           refreshAdapter();
        }

        @Override
        public void removeItem(Object object) {
            for (int i=0;i<getDataList().size();i++)
            {
                if (isEquals(object,getDataList().get(i)))
                {
                    removeItem(i);
                }
            }
        }

        @Override
        public void removeItem(int position) {
            getDataList().remove(position);
           refreshAdapter();
        }

        @Override
        public void insertItem(int position, Object object) {
            getDataList().add(position,object);
            refreshAdapter();
        }

        @Override
        protected void resolveRefreshData(Object object) {
            super.resolveRefreshData(object);

            if (object == null) return;

            if (object instanceof ListBehavior)
                pointer.set((ListBehavior) object);
            else
                throw new RuntimeException(object.getClass().getSimpleName() + " Must be implements ListBehavior");
        }

        @Override
        protected void resolveLoadmoreData(Object object) {
            super.resolveLoadmoreData(object);

            if (object == null) return;

            if (object instanceof ListBehavior)
                addDatas(((ListBehavior) object).getList());
            else
                throw new RuntimeException(object.getClass().getSimpleName() + " Must be implements ListBehavior");
        }

        @Override
        protected boolean isEmptyData(Object object) {
            if (object instanceof ListBehavior)
            {
                if (isRefresh() && !getRoleList().isEmpty())
                {
                    boolean isEmpty = true;
                    for (String role : getRoleList())
                    {
                        if (super.isEmptyData(((ListBehavior) object).getList(role))) continue;
                        isEmpty = super.isEmptyData(((ListBehavior) object).getList(role));
                    }
                    return isEmpty;
                }
                else
                {
                    return super.isEmptyData(((ListBehavior) object).getList());
                }
            }
            else
                return super.isEmptyData(object);
        }

        @Override
        public List getDataList() {
            if (pointer == null || pointer.get() == null) return null;
            return pointer.get().getList();
        }

        public void addDatas(List list) {
            getDataList().addAll(list);
        }

        //初始化适配器，可以选择重写该方法，在初始化adapter时传入更多参数
        protected void initAdapter(){
            initAdapter(pointer);
        }

        protected boolean isEquals(Object one, Object two){

            if (one == null && two == null) return true;

            if (one == null || two == null) return false;

            //如果one two存在继承关系且都实现了IdBehavior且设置过Id字段（id!=0）,那么以id是否相同作为比较依据
            if (one instanceof IdBehavior && two instanceof IdBehavior && (one.getClass().isAssignableFrom(two.getClass())||two.getClass().isAssignableFrom(one.getClass())) && ((IdBehavior) one).getId() != null && ((IdBehavior) two).getId() != null)
                return ((IdBehavior) one).getId().equals(((IdBehavior) two).getId());

            return one.equals(two);
        }



        ///////////////////////////////////////////////////////////////////////////
        // V
        ///////////////////////////////////////////////////////////////////////////
        @Deprecated
        @Override
        public List<String> getRoleList() {
            return ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).getRoleList();
        }

        @Deprecated
        @Override
        public void initAdapter(Pointer<ListBehavior> pointer, Object... objects) {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).initAdapter(pointer,objects);
        }

        @Deprecated
        @Override
        public void refreshAdapter() {
            ((IBaseRefreshLoadListBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshAdapter();
        }

    }

}
