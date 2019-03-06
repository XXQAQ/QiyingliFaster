package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.bean.behavior.IdBehavior;
import com.xq.androidfaster.bean.behavior.ListBehavior;
import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadListPresenter<T extends IBaseRefreshLoadListView> extends IAbsRefreshLoadListPresenter<T>, IBaseRefreshLoadPresenter<T> {

    @Override
    default void initAdapter(){
        getRefreshLoadDelegate().initAdapter();
    }

    @Override
    default void addDatas(List list) {
        getRefreshLoadDelegate().addDatas(list);
    }

    @Override
    default void addDifferentDatas(List list) {
        getRefreshLoadDelegate().addDifferentDatas(list);
    }

    @Override
    default void clearDatas() {
        getRefreshLoadDelegate().clearDatas();
    }

    @Override
    default void refreshItem(int position, Object object) {
     getRefreshLoadDelegate().refreshItem(position,object);
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

    @Override
    default List getDataList() {
        return getRefreshLoadDelegate().getDataList();
    }

    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadListView> extends IBaseRefreshLoadPresenter.RefreshLoadDelegate<T> implements IAbsRefreshLoadListPresenter<T> {

        protected List list_data = new LinkedList<>();

        public RefreshLoadDelegate(IAbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void initAdapter(){
            getBindView().initAdapter(getDataList());
        }

        @Override
        public void addDatas(List list) {
            getDataList().addAll(list);
        }

        @Override
        public void addDifferentDatas(List list) {
            if (list == null)
                list = new LinkedList();

            for (int i=0;i<list.size();i++)
            {
                Object newObject = list.get(i);

                if (newObject == null) continue;

                if (newObject instanceof IdBehavior)
                {
                    for (int j=0;j<getDataList().size();j++)
                    {
                        Object oldObject = getDataList().get(j);
                        if (oldObject instanceof IdBehavior && ((IdBehavior) oldObject).getId() == ((IdBehavior) newObject).getId())
                        {
                            getDataList().remove(j);
                            break;
                        }
                    }
                    getDataList().add(newObject);
                }
                else
                {
                    for (int j=0;j<getDataList().size();j++)
                    {
                        Object oldObject = getDataList().get(j);
                        if (newObject.equals(oldObject))
                        {
                            getDataList().remove(j);
                            break;
                        }
                    }
                    getDataList().add(newObject);
                }
            }
        }

        @Override
        public void clearDatas() {
            getDataList().clear();
        }

        @Override
        public void refreshItem(int position, Object object) {
            if (object == null)
                return;

            getDataList().remove(position);
            getDataList().add(position, object);

            refreshItem(position);
        }

        @Override
        public void refreshItem(Object object) {
            if (object == null)
                return;

            if (object instanceof IdBehavior)
            {
                for (int i=0;i<getDataList().size();i++)
                {
                    Object oldObject = getDataList().get(i);
                    if (oldObject instanceof IdBehavior && ((IdBehavior) oldObject).getId() == ((IdBehavior) object).getId())
                    {
                        refreshItem(i);
                        break;
                    }
                }
            }
            else
            {
                for (int i=0;i<getDataList().size();i++)
                {
                    if (object.equals(getDataList().get(i)))
                    {
                        refreshItem(i);
                        break;
                    }
                }
            }
        }

        @Override
        public void refreshItem(int position) {
            getBindView().refreshItemView(position);
        }

        @Override
        public void removeItem(Object object) {
            if (object == null)
                return;

            if (object instanceof IdBehavior)
            {
                for (int i=0;i<getDataList().size();i++)
                {
                    Object oldObject = getDataList().get(i);
                    if (oldObject instanceof IdBehavior && ((IdBehavior) oldObject).getId() == ((IdBehavior) object).getId())
                    {
                        removeItem(i);
                        break;
                    }
                }
            }
            else
            {
                for (int i=0;i<getDataList().size();i++)
                {
                    if (object.equals(getDataList().get(i)))
                    {
                        removeItem(i);
                        break;
                    }
                }
            }
        }

        @Override
        public void removeItem(int position) {
            getDataList().remove(position);
            getBindView().removeItemView(position);
        }

        @Override
        public void insertItem(int position, Object object) {
            getDataList().add(position,object);
            getBindView().insertItemView(position);
        }

        @Override
        public List getDataList() {
            return list_data;
        }

        @Override
        protected boolean isEmptyData(Object object) {
            if (object instanceof ListBehavior)
                return super.isEmptyData(((ListBehavior) object).getList());
            else
                return super.isEmptyData(object);
        }
    }

}
