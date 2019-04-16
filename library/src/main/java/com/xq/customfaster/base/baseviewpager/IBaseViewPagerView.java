package com.xq.customfaster.base.baseviewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.bean.behavior.FragmentTitleBehavior;
import java.util.List;

public interface IBaseViewPagerView<T extends IBaseViewPagerPresenter> extends IAbsViewPagerView<T> {

    @Override
    default void refreshViewPager(){
        getViewPagerDelegate().refreshViewPager();
    }

    public ViewPagerDelegate getViewPagerDelegate();

    public class ViewPagerDelegate<T extends IBaseViewPagerPresenter> extends AbsViewDelegate<T> implements IAbsViewPagerView<T> {

        public ViewPager viewPager;
        public TabLayout tabLayout;

        public ViewPagerDelegate(IAbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {
            super.afterOnCreate(savedInstanceState);

            viewPager = (ViewPager) findViewById(getContext().getResources().getIdentifier("viewPager", "id", getContext().getPackageName()));

            tabLayout = (TabLayout) findViewById(getContext().getResources().getIdentifier("tabLayout", "id", getContext().getPackageName()));

        }

        @Override
        public void refreshViewPager(){
            viewPager.getAdapter().notifyDataSetChanged();
        }

        public void initViewPager(List<FragmentTitleBehavior> list) {

            if (list != null)
            {
                for (int i=0;i<list.size();i++)
                {
//                    tabLayout.addTab(tabLayout.newTab());
                }
            }

            if (tabLayout != null) tabLayout.setupWithViewPager(viewPager);

            viewPager.setAdapter(new UniverseFragmentPagerAdapter(getCPFragmentManager(),list));
        }

        //万能FragmentPagerAdapter
        public class UniverseFragmentPagerAdapter extends FragmentStatePagerAdapter {

            private List<FragmentTitleBehavior> list;

            public UniverseFragmentPagerAdapter(FragmentManager fm, List<FragmentTitleBehavior> list) {
                super(fm);
                this.list = list;
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position).getFragment();
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (list == null)
                    return super.getPageTitle(position);
                else
                    return list.get(position).getTitle();
            }
        }
    }

}