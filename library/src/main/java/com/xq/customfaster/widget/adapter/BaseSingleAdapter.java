package com.xq.customfaster.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

public abstract class BaseSingleAdapter extends DelegateAdapter.Adapter {

    private Context context;

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Deprecated
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();

        //自定义布局可通过createView或getLayoutId创建（默认优先采用createView的返回值，如果调用者没有重写该方法则使用getLayoutId返回值来创建View）
        View view = createView(viewGroup,viewType);if (view == null) view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(viewType),viewGroup,false);

        return new BaseViewHolder(view,viewType);
    }

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Deprecated
    @Override
    public int getItemCount() {
        return 1;
    }

    protected Context getContext() {
        return context;
    }

    protected View createView(ViewGroup viewGroup, int viewType) {
        return null;
    }

    protected abstract int getLayoutId(int viewType);

}