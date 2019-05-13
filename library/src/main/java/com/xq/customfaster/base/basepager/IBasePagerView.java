package com.xq.customfaster.base.basepager;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.worldbean.bean.behavior.FragmentBehavior;
import com.xq.worldbean.bean.behavior.TitleBehavior;
import java.util.List;

public interface IBasePagerView<T extends IBasePagerPresenter> extends IAbsPagerView<T> {

    @Override
    default void initPager(List<FragmentBehavior> list) {
        getPagerDelegate().initPager(list);
    }

    @Override
    default void refreshPager(){
        getPagerDelegate().refreshPager();
    }

    public PagerDelegate getPagerDelegate();

    public class PagerDelegate<T extends IBasePagerPresenter> extends AbsViewDelegate<T> implements IAbsPagerView<T> {

        public ViewPager viewPager;
        public TabLayout tabLayout;

        public PagerDelegate(IAbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            viewPager = (ViewPager) findViewById(getContext().getResources().getIdentifier("viewPager", "id", getContext().getPackageName()));

            tabLayout = (TabLayout) findViewById(getContext().getResources().getIdentifier("tabLayout", "id", getContext().getPackageName()));

        }

        @Override
        public void refreshPager(){
            viewPager.getAdapter().notifyDataSetChanged();
        }

        public void initPager(List<FragmentBehavior> list) {

            if (tabLayout != null) tabLayout.setupWithViewPager(viewPager);

            viewPager.setAdapter(new DefaultFragmentPagerAdapter(getCPFragmentManager(),list));

        }

        public class DefaultFragmentPagerAdapter extends FragmentStatePagerAdapter {

            private List<FragmentBehavior> list;

            public DefaultFragmentPagerAdapter(FragmentManager fm, List<FragmentBehavior> list) {
                super(fm);
                this.list = list;
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position).createFragment();
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public void restoreState(Parcelable state, ClassLoader loader) {
                if (view.isSaveFragmentState()) super.restoreState(state,loader);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (list.get(position) instanceof TitleBehavior) return ((TitleBehavior) list.get(position)).getTitle();
                return super.getPageTitle(position);
            }
        }
    }

}