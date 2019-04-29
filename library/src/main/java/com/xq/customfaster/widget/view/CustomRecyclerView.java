package com.xq.customfaster.widget.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class CustomRecyclerView extends RecyclerView implements RecyclerViewInterface {

    private View emptyView;

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
    public void addHeaderView(Object object) {
    }

    @Override
    public void addFooterView(Object object) {
    }

    @Override
    public void setEmptyView(Object object) {
        if (object instanceof View) emptyView = (View) object;
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }

    @Override
    public int getFooterCount() {
        return 0;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(mObserver);
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            boolean emptyViewVisible = getAdapter().getItemCount() == 0;

            // 显示为空视图
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            // RecyclerView本身隐藏
            this.setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
        }
    }

    private AdapterDataObserver mObserver = new AdapterDataObserver() {

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onChanged() {
            checkIfEmpty();
        }
    };

}
