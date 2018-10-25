package com.xq.customfaster.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

public class CustomRecyclerView extends FamiliarRecyclerView implements RecyclerViewInterface{

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addHeadView(Object object) {
        super.addHeaderView((View) object);
    }

    @Override
    public void addFootView(Object object) {
        super.addFooterView((View) object);
    }

    @Override
    public void setEmptyView(Object object) {
        super.setEmptyView((View) object,true);
    }

}
