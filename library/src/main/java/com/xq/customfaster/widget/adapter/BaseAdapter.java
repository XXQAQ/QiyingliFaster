package com.xq.customfaster.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xq.customfaster.R;
import com.xq.worldbean.bean.behavior.ListBehavior;
import com.xq.worldbean.util.Pointer;
import java.util.List;

public abstract class BaseAdapter extends AbsAdapter {

    protected static final int TYPE_EMPTY = 0x100;  //VLayout框架会对ViewType进行判断，小于0的话不会执行后续处理，谨记此坑！

    private Pointer<ListBehavior> pointer;
    private String listRole;
    private List list;

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
    @Override
    public int getItemCount() {
        if (getList() == null || getList().isEmpty())   return 1;
        return super.getItemCount();
    }

    @Deprecated
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && getList() == null || getList().isEmpty())   return TYPE_EMPTY;
        return getViewType(position);
    }

    @Override
    public int getCount() {
        if (getList() == null || getList().isEmpty())   return 0;
        return getList().size();
    }

    @Override
    protected SpecialViewHolder createSpecialViewHolder(int viewType) {
        if (viewType == TYPE_EMPTY)
        {
            View view = createEmptyView();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
            return new SpecialViewHolder(view,viewType);
        }
        return super.createSpecialViewHolder(viewType);
    }

    protected View createEmptyView() {
        return new View(getContext());
    }

    protected List getList() {
        if (pointer != null && pointer.get() != null)  list =  pointer.get().getList(listRole);
        return list;
    }

    //以下均为便捷设置空布局的方法
    protected View getDefaultEmptyView(){
        return getDefaultEmptyView(null);
    }

    protected View getDefaultEmptyView(CharSequence title){
        return getDefaultEmptyView(title,0);
    }

    protected View getDefaultEmptyView(CharSequence title, int imageRes) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty,getParent(),false);

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
}
