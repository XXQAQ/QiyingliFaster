package com.xq.customfaster.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.xq.customfaster.R;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;

import java.util.List;

public abstract class BaseAdapter extends DelegateAdapter.Adapter {

    protected static final int TYPE_EMPTY = 0x100;  //VLayout框架会对ViewType进行判断，小于0的话不会执行后续处理，谨记此坑！

    private Context context;

    private Pointer<ListBehavior> pointer;
    private String listRole;
    private List list;

    //一般情况下不建议调用无参构造方法
    @Deprecated
    public BaseAdapter() {
    }

    public BaseAdapter(Pointer<ListBehavior> pointer) {
        this.pointer = pointer;
    }

    public BaseAdapter(Pointer<ListBehavior> pointer, String listRole) {
        this.pointer = pointer;
        this.listRole = listRole;
    }

    public BaseAdapter(List list) {
        this.list = list;
    }

    @Deprecated
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();

        //如果当前viewType是空布局,则使用默认空布局ViewHolder
        if (viewType == TYPE_EMPTY) return new EmptyViewHolder(createEmptyView(viewGroup));

        //自定义布局可通过createView或getLayoutId创建（默认优先采用createView的返回值，如果调用者没有重写该方法则使用getLayoutId返回值来创建View）
        View view = createView(viewGroup,viewType);if (view == null) view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(viewType),viewGroup,false);

        return new BaseViewHolder(view,viewType);
    }

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ///空布局不需要作任何处理
        if (holder instanceof EmptyViewHolder)  return;

        convertView((BaseViewHolder) holder,position);
        convertListener((BaseViewHolder) holder,position);
    }

    @Deprecated
    @Override
    public int getItemCount() {
        if (getList() == null || getList().isEmpty())   return 1;
        return getList().size();
    }

    @Deprecated
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && getList() == null || getList().isEmpty())   return TYPE_EMPTY;
        return getViewType(position);
    }

    protected int getViewType(int position) {
        return 0;
    }

    protected View createView(ViewGroup viewGroup, int viewType) {
        return null;
    }

    protected abstract int getLayoutId(int viewType);

    protected abstract void convertView(BaseViewHolder holder, int position);

    protected abstract void convertListener(BaseViewHolder holder, int position);

    protected View createEmptyView(ViewGroup viewGroup) {
        return new View(getContext());
    }

    protected Context getContext() {
        return context;
    }

    protected Pointer<ListBehavior> getPointer() {
        return pointer;
    }

    protected List getList() {
        if (pointer != null && pointer.get() != null)  list =  pointer.get().getList(listRole);
        return list;
    }

    //以下均为便捷设置空布局的方法
    protected View getDefaultEmptyView(ViewGroup viewGroup){
        return getDefaultEmptyView(viewGroup,null);
    }

    protected View getDefaultEmptyView(ViewGroup viewGroup, CharSequence title){
        return getDefaultEmptyView(viewGroup,title,0);
    }

    protected View getDefaultEmptyView(ViewGroup viewGroup, CharSequence title, int imageRes) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty,viewGroup,false);

        if (TextUtils.isEmpty(title)) title = "没有更多数据啦";
        TextView textView = view.findViewById(R.id.titleView);
        textView.setText(title);

        if (imageRes != 0)
        {
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(imageRes);
        }

        return view;
    }

    protected static class EmptyViewHolder extends BaseViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
