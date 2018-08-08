package com.xq.customfaster.base.baseviewpager;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.xq.projectdefine.base.abs.AbsView;
import com.xq.projectdefine.base.abs.AbsViewDelegate;
import com.xq.projectdefine.bean.behavior.TitleBehavior;

import java.util.LinkedList;
import java.util.List;

public interface IBaseViewPagerView<T extends IBaseViewPagerPresenter> extends AbsViewPagerView<T> {

    @Override
    default void refreshViewPager(){
        getViewPagerDelegate().refreshViewPager();
    }

    public ViewPagerDelegate getViewPagerDelegate();

    public class ViewPagerDelegate<T extends IBaseViewPagerPresenter> extends AbsViewDelegate<T> implements AbsViewPagerView<T>{

        public ViewPager vp;
        public TabLayout tl;

        public ViewPagerDelegate(AbsView view) {
            super(view);
        }

        @Override
        public void afterOnCreate(Bundle savedInstanceState) {

            if (getRootView() instanceof ViewPager)   //如果根布局是ViewPager，则直接将根布局转化为vp
                vp = (ViewPager) getRootView();
            else
                vp = (ViewPager) getRootView().findViewById(getContext().getResources().getIdentifier("vp", "id", getContext().getPackageName()));

            tl = (TabLayout) getRootView().findViewById(getContext().getResources().getIdentifier("tl", "id", getContext().getPackageName()));

            if (tl != null)
            {
                if (getTabTextNormalColor() >= 0 || getTabTextSelectColor()>= 0)
                    tl.setTabTextColors(getTabTextNormalColor(),getTabTextSelectColor());
                if (getTabBackgroundColor() >= 0)
                    tl.setBackgroundColor(getTabBackgroundColor());
            }

            initViewPager(getPresenter().getFragmentsAndTitles());
        }

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {

        }

        @Override
        public void refreshViewPager(){
            vp.getAdapter().notifyDataSetChanged();
        }

        protected void initViewPager(List<TitleBehavior> list) {

            List<CharSequence> list_title = new LinkedList<>();
            List<Fragment> list_fragment = new LinkedList<>();

            if (list != null && tl != null)
            {
                for (int i=0;i<list.size();i++)
                {
                    tl.addTab(tl.newTab());
                    list_title.add(list.get(i).getTitle());
                    list_fragment.add((Fragment) list.get(i).getTag());
                }
                tl.setupWithViewPager(vp);
            }

            vp.setAdapter(new UniverseFragmentPagerAdapter(getCPFragmentManager(),list_fragment,list_title));
        }

        //返回Tab文字正常时颜色
        protected int getTabTextNormalColor() {
            return -1;
        }

        //返回Tab文字选择时颜色
        protected int getTabTextSelectColor() {
            return -1;
        }

        //返回Tab文字背景色
        protected int getTabBackgroundColor() {
            return -1;
        }

    }

}