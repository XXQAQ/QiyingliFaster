package com.xq.customfaster.base.baserefreshload;


import com.xq.customfaster.base.basesimplerefreshload.IBaseSimpleRefreshLoadPresenter;
import com.xq.projectdefine.base.abs.AbsPresenter;

import java.util.LinkedList;
import java.util.List;

public interface IBaseRefreshLoadPresenter<T extends AbsRefreshLoadView> extends AbsRefreshLoadPresenter<T> ,IBaseSimpleRefreshLoadPresenter<T> {

    @Override
    default void initAdapter(){
        getRefreshLoadBuilder().initAdapter();
    }

    @Override
    default List getListData() {
        return getRefreshLoadBuilder().getListData();
    }

    @Override
    public RefreshLoadBuilder getRefreshLoadBuilder();

    public abstract class RefreshLoadBuilder<T extends AbsRefreshLoadView> extends IBaseSimpleRefreshLoadPresenter.RefreshLoadBuilder<T> implements AbsRefreshLoadPresenter<T>{

        public List list_data = new LinkedList<>();

        public RefreshLoadBuilder(AbsPresenter presenter) {
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
