package com.xq.qiyinglifaster.widget.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xq on 2017/8/2.
 */

public abstract class BaseDelegate implements ItemViewDelegate {

    protected Context context;
    protected List list;

    public BaseDelegate(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        initView(holder,o,position);
        initListener(holder,o,position);
    }

    protected abstract void initListener(ViewHolder holder, Object o, int position);

    protected abstract void initView(ViewHolder holder, Object o, int position);
}
