package com.xq.customfaster.base.baserefreshloadlist;


import com.xq.customfaster.base.baserefreshload.IBaseRefreshLoadPresenter;
import com.xq.projectdefine.base.abs.AbsPresenter;
import com.xq.projectdefine.bean.behavior.ListBehavior;
import com.xq.projectdefine.util.tools.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadListPresenter<T extends IBaseRefreshLoadListView> extends AbsRefreshLoadListPresenter<T>, IBaseRefreshLoadPresenter<T> {

    @Override
    default void initAdapter(){
        getRefreshLoadDelegate().initAdapter();
    }

    @Override
    default List getListData() {
        return getRefreshLoadDelegate().getListData();
    }

    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadListView> extends IBaseRefreshLoadPresenter.RefreshLoadDelegate<T> implements AbsRefreshLoadListPresenter<T> {

        public List list_data = new LinkedList<>();

        public RefreshLoadDelegate(AbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void initAdapter(){
            getBindView().initAdapter(list_data);
        }

        @Override
        public List getListData() {
            return list_data;
        }

        @Override
        protected boolean isEmptyData(boolean isOperateSuccess,Object object) {
            if (isOperateSuccess)
                if (object instanceof ListBehavior)
                    return super.isEmptyData(isOperateSuccess,((ListBehavior) object).getList());
                else
                    return super.isEmptyData(isOperateSuccess,object);
            else
                return true;
        }
    }

}
