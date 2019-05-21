package com.xq.customfaster.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private int viewType;
    private SparseArray<View> array = new SparseArray<View>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;
    }

    private <T extends View> T findViewById(int viewId) {
        View view = array.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            array.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends View> T getView(int viewId) {
        return findViewById(viewId);
    }

    public ImageView getImageView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return findViewById(viewId);
    }

    public ViewGroup getViewGroup(int viewId){
        return findViewById(viewId);
    }

    public int getViewType() {
        return viewType;
    }

}
