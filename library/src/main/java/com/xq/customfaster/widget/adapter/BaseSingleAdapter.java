package com.xq.customfaster.widget.adapter;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

public abstract class BaseSingleAdapter extends AbsAdapter {

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    protected void convertView(BaseViewHolder holder, int position) {

    }

    @Override
    protected void convertListener(BaseViewHolder holder, int position) {

    }
}
