package com.xq.customfaster.widget.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

public class VLayoutHorizontalAdapter extends DelegateAdapter.Adapter{

    private RecyclerView.Adapter adapter;
    private int spanCount = 1;
    private RecyclerView.ItemDecoration decoration;

    public VLayoutHorizontalAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public VLayoutHorizontalAdapter(RecyclerView.Adapter adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;
    }

    public VLayoutHorizontalAdapter(RecyclerView.Adapter adapter, int spanCount, RecyclerView.ItemDecoration decoration) {
        this.adapter = adapter;
        this.spanCount = spanCount;
        this.decoration = decoration;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView rv = new RecyclerView(viewGroup.getContext());
        rv.setLayoutManager(new GridLayoutManager(viewGroup.getContext(),spanCount,RecyclerView.HORIZONTAL,false));
        if (decoration != null) rv.addItemDecoration(decoration);
        rv.setAdapter(adapter);
        return new BaseViewHolder(rv,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }
}
