package com.xq.customfaster.base.baserefreshload;


import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadPresenter;
import com.xq.projectdefine.base.abs.AbsPresenter;

import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadPresenter<T extends IBaseRefreshLoadView> extends AbsRefreshLoadPresenter<T> ,IBaseSimpleRefreshLoadPresenter<T> {

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

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadView> extends IBaseSimpleRefreshLoadPresenter.RefreshLoadDelegate<T> implements AbsRefreshLoadPresenter<T>{

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
    }

}
