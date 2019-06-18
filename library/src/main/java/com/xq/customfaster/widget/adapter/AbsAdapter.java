package com.xq.customfaster.widget.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

public abstract class AbsAdapter extends DelegateAdapter.Adapter  {

    private Context context;

    private RecyclerView parent;

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return null;
    }

    @Deprecated
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        this.context = getReallyActivityContext(viewGroup.getContext());
        this.parent = (RecyclerView) viewGroup;

        RecyclerView.ViewHolder holder;

        //头布局 尾布局 空布局 都属于SpecialViewHolder这一类
        holder = createSpecialViewHolder(viewType);
        if (holder != null) return holder;

        //自定义布局可通过createView或getLayoutId创建（默认优先采用createView的返回值，如果调用者没有重写该方法则使用getLayoutId返回值来创建View）
        View view = createView(viewType);
        if (view == null)
        {
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(viewType),viewGroup,false);
        }
        else
        {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
        }
        holder = new BaseViewHolder(view,viewType);
        return holder;
    }

    protected Activity getReallyActivityContext(Context context) {
        //兼容安卓5.0以下在View中获取Context并非真实Activity Context的问题
        while (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        throw new IllegalStateException("The Context is not an Activity.");
    }

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SpecialViewHolder)    return;

        convertView((BaseViewHolder) holder,position);
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

    public Context getContext() {
        return context;
    }

    public RecyclerView getParent(){
        return parent;
    }

    protected SpecialViewHolder createSpecialViewHolder(int viewType) {
        return null;
    }

    protected View createView(int viewType) {
        return null;
    }

    protected int getViewType(int position) {
        return 0;
    }

    public abstract int getCount();

    protected abstract int getLayoutId(int viewType);

    protected abstract void convertView(BaseViewHolder holder, int position);

}
