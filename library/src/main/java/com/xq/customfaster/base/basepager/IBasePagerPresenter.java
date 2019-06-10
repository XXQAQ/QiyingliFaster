package com.xq.customfaster.base.basepager;

import android.os.Bundle;
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.worldbean.bean.behavior.FragmentBehavior;
import java.util.List;

public interface IBasePagerPresenter<T extends IBasePagerView> extends IAbsPagerPresenter<T> {

    @Override
    default List<FragmentBehavior> getFragmentBehaviorList() {
        return getPagerDelegate().getFragmentBehaviorList();
    }

    public PagerDelegate getPagerDelegate();

    public abstract class PagerDelegate<T extends IBasePagerView> extends AbsPresenterDelegate<T> implements IAbsPagerPresenter<T> {

        protected List<FragmentBehavior> list_fragmentBehavior;

        public PagerDelegate(IAbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            list_fragmentBehavior = createFragmentBehaviorList();
            getBindView().initPager(getFragmentBehaviorList());
        }

        @Override
        public List<FragmentBehavior> getFragmentBehaviorList() {
            return list_fragmentBehavior;
        }

        protected abstract List<FragmentBehavior> createFragmentBehaviorList();

    }

}