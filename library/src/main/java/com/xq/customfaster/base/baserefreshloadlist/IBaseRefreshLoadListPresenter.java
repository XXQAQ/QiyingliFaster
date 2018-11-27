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
    default List getListData() {
        return getRefreshLoadDelegate().getListData();
    }

    @Override
    default List getDataList() {
        return getRefreshLoadDelegate().getDataList();
    }

    @Override
    public RefreshLoadDelegate getRefreshLoadDelegate();

    public abstract class RefreshLoadDelegate<T extends IBaseRefreshLoadListView> extends IBaseRefreshLoadPresenter.RefreshLoadDelegate<T> implements IAbsRefreshLoadListPresenter<T> {

        public List list_data = new LinkedList<>();

        public RefreshLoadDelegate(IAbsPresenter presenter) {
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
        public List getDataList() {
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
