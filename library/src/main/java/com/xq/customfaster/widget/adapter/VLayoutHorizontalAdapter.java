package com.xq.customfaster.widget.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

public class VLayoutHorizontalAdapter extends AbsAdapter{

    private RecyclerView.Adapter adapter;
    private RecyclerView.ItemDecoration decoration;

    public VLayoutHorizontalAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public VLayoutHorizontalAdapter(RecyclerView.Adapter adapter,RecyclerView.ItemDecoration decoration) {
        this.adapter = adapter;
        this.decoration = decoration;
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        RecyclerView rv = new RecyclerView(viewGroup.getContext());
        rv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rv.setLayoutManager(new GridLayoutManager(viewGroup.getContext(),1,RecyclerView.HORIZONTAL,false));
        if (decoration != null) rv.addItemDecoration(decoration);
        rv.setAdapter(adapter);
        return rv;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    protected void convertView(BaseViewHolder holder, int position) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void convertListener(BaseViewHolder holder, int position) {

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }
}
