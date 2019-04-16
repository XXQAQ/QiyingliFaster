package com.xq.customfaster.base.baseviewpager;

import android.os.Bundle;
import com.xq.androidfaster.base.abs.AbsPresenterDelegate;
import com.xq.androidfaster.base.abs.IAbsPresenter;
import com.xq.androidfaster.bean.behavior.FragmentTitleBehavior;
import java.util.List;

public interface IBaseViewPagerPresenter<T extends IBaseViewPagerView> extends IAbsViewPagerPresenter<T> {

    public ViewPagerDelegate getViewPagerDelegate();

    public abstract class ViewPagerDelegate<T extends IBaseViewPagerView> extends AbsPresenterDelegate<T> implements IAbsViewPagerPresenter<T>{

        protected List<FragmentTitleBehavior> list_fragmentTitle;

        public ViewPagerDelegate(IAbsPresenter presenter) {
            super(presenter);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            list_fragmentTitle = createFragmentTitleList();
            getBindView().initViewPager(getFragmentTitleList());
        }

        @Override
        public List<FragmentTitleBehavior> getFragmentTitleList() {
            return list_fragmentTitle;
        }

        protected abstract List<FragmentTitleBehavior> createFragmentTitleList();

    }

}