package com.xq.customfaster.base.baserefreshloadlist;

import com.xq.androidfaster.base.abs.IAbsPresenter;
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
            getBindView().initAdapter(list_data);
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
