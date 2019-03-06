package com.xq.customfaster.base.baseviewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.xq.androidfaster.base.abs.AbsViewDelegate;
import com.xq.androidfaster.base.abs.IAbsView;
import com.xq.androidfaster.bean.behavior.TitleBehavior;
import java.util.LinkedList;
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

            initViewPager(getBindPresenter().getFragmentsAndTitles());
        }

        @Override
        public void refreshViewPager(){
            viewPager.getAdapter().notifyDataSetChanged();
        }

        protected void initViewPager(List<TitleBehavior> list) {

            List<CharSequence> list_title = new LinkedList<>();
            List<Fragment> list_fragment = new LinkedList<>();

            if (list != null && tabLayout != null)
            {
                for (int i=0;i<list.size();i++)
                {
                    tabLayout.addTab(tabLayout.newTab());
                    list_title.add(list.get(i).getTitle());
                    list_fragment.add((Fragment) list.get(i).getTag());
                }
                tabLayout.setupWithViewPager(viewPager);
            }

            viewPager.setAdapter(new UniverseFragmentPagerAdapter(getCPFragmentManager(),list_fragment,list_title));
        }
    }

}