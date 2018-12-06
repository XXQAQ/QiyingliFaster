package com.xq.customfaster.widget.view;

public interface RecyclerViewInterface {

    public void addHeadView(Object object);

    public void addFootView(Object object);

    public int getHeadCount();

    public int getFootCount();

    public void setEmptyView(Object object);

}
