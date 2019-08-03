package com.xq.customfaster.base.basepager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.base.core.Controler;
import com.xq.androidfaster.base.delegate.BaseDelegate;
import com.xq.worldbean.bean.behavior.FragmentBehavior;
import com.xq.worldbean.bean.behavior.TitleBehavior;
import java.util.List;

public interface IBasePagerView extends IBasePagerBehavior {

    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default void initPager(List list) {
        getPagerDelegate().initPager(list);
    }

    @Override
    default void refreshPager(){
        getPagerDelegate().refreshPager();
    }



    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    @Deprecated
    @Override
    default List<FragmentBehavior> getFragmentBehaviorList() {
        return getPagerDelegate().getFragmentBehaviorList();
    }

    public PagerDelegate getPagerDelegate();

    public class PagerDelegate extends BaseDelegate implements IBasePagerBehavior {

        public ViewPager viewPager;
        public TabLayout tabLayout;

        public PagerDelegate(Controler controler) {
            super(controler);
        }

        @Override
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            viewPager = (ViewPager) findViewById(getContext().getResources().getIdentifier("viewPager", "id", getContext().getPackageName()));

            tabLayout = (TabLayout) findViewById(getContext().getResources().getIdentifier("tabLayout", "id", getContext().getPackageName()));

        }

        @Override
        public void refreshPager(){
            viewPager.getAdapter().notifyDataSetChanged();
        }

        public void initPager(List list) {

            if (tabLayout != null) tabLayout.setupWithViewPager(viewPager);

            viewPager.setAdapter(new DefaultFragmentPagerAdapter(getCPFragmentManager(),list));

        }



        ///////////////////////////////////////////////////////////////////////////
        // P
        ///////////////////////////////////////////////////////////////////////////
        @Deprecated
        @Override
        public List<FragmentBehavior> getFragmentBehaviorList() {
            return ((IBasePagerBehavior)((IFasterBaseBehavior)getControler()).getBindAnother()).getFragmentBehaviorList();
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
            public CharSequence getPageTitle(int position) {
                if (list.get(position) instanceof TitleBehavior) return ((TitleBehavior) list.get(position)).getTitle();
                return super.getPageTitle(position);
            }
        }
    }
}