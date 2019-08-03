package com.xq.customfaster.base.basepager;

import android.os.Bundle;
import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.base.core.Controler;
import com.xq.androidfaster.base.delegate.BaseDelegate;
import com.xq.worldbean.bean.behavior.FragmentBehavior;
import java.util.List;

public interface IBasePagerPresenter extends IBasePagerBehavior{

    public PagerDelegate getPagerDelegate();

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////
    @Override
    default List<FragmentBehavior> getFragmentBehaviorList() {
        return getPagerDelegate().getFragmentBehaviorList();
    }



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    @Deprecated
    @Override
    default void initPager(List list) {
        getPagerDelegate().initPager(list);
    }

    @Deprecated
    @Override
    default void refreshPager(){
        getPagerDelegate().refreshPager();
    }

    public abstract class PagerDelegate extends BaseDelegate implements IBasePagerBehavior {

        protected List<FragmentBehavior> list_fragmentBehavior;

        public PagerDelegate(Controler controler) {
            super(controler);
        }

        @Override
        public void create(Bundle savedInstanceState) {
            super.create(savedInstanceState);

            list_fragmentBehavior = createFragmentBehaviorList();
            initPager(getFragmentBehaviorList());
        }

        @Override
        public List<FragmentBehavior> getFragmentBehaviorList() {
            return list_fragmentBehavior;
        }

        protected abstract List createFragmentBehaviorList();



        ///////////////////////////////////////////////////////////////////////////
        // V
        ///////////////////////////////////////////////////////////////////////////
        @Deprecated
        @Override
        public void refreshPager() {
            ((IBasePagerView)((IFasterBaseBehavior)getControler()).getBindAnother()).refreshPager();
        }

        @Deprecated
        @Override
        public void initPager(List list) {
            ((IBasePagerView)((IFasterBaseBehavior)getControler()).getBindAnother()).initPager(list);
        }
    }

}