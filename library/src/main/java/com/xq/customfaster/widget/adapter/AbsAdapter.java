package com.xq.customfaster.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.android.vlayout.DelegateAdapter;

public abstract class AbsAdapter extends DelegateAdapter.Adapter  {

    private Context context;

    @Deprecated
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();

        RecyclerView.ViewHolder holder;

        holder = createSpecialViewHolder(viewGroup,viewType);
        if (holder != null) return holder;

        //自定义布局可通过createView或getLayoutId创建（默认优先采用createView的返回值，如果调用者没有重写该方法则使用getLayoutId返回值来创建View）
        View view = createView(viewGroup,viewType);if (view == null) view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(viewType),viewGroup,false);

        holder = new BaseViewHolder(view,viewType);
        return holder;
    }

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SpecialViewHolder)    return;

        convertView((BaseViewHolder) holder,position);
        convertListener((BaseViewHolder) holder,position);
    }

    @Deprecated
    @Override
    public int getItemCount() {
        return getCount();
    }

    @Deprecated
    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    public abstract int getCount();

    protected int getViewType(int position) {
        return 0;
    }

    protected SpecialViewHolder createSpecialViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    protected View createView(ViewGroup viewGroup, int viewType) {
        return null;
    }

    protected abstract int getLayoutId(int viewType);

    protected abstract void convertView(BaseViewHolder holder, int position);

    protected abstract void convertListener(BaseViewHolder holder, int position);

    protected Context getContext() {
        return context;
    }

}
