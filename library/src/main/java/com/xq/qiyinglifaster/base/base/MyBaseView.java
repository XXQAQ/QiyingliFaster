package com.xq.qiyinglifaster.base.base;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xq.customview.view.IconFontTextView;
import com.xq.projectdefine.util.tools.BarUtils;
import com.xq.projectdefine.util.tools.DensityUtils;
import com.xq.projectdefine.util.tools.ImageUtils;
import com.xq.qiyinglifaster.R;
import com.xq.projectdefine.base.base.FasterBaseView;
import com.yanzhenjie.statusview.StatusUtils;

public abstract class MyBaseView<T extends IMyBasePresenter> extends FasterBaseView<T> implements IMyBaseView<T> {

    protected Toolbar toolbar;

    protected NestedScrollView sv;

    public MyBaseView(T presenter) {
        super(presenter);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        toolbar = (Toolbar) rootView.findViewById(getContext().getResources().getIdentifier("toolbar", "id", getContext().getPackageName()));

        sv = (NestedScrollView)findViewById(getContext().getResources().getIdentifier("sv", "id", getContext().getPackageName()));

        if (sv != null)
            sv.setNestedScrollingEnabled(false);

        if (isHideSystemBar())
            hideSystemBar();

        //针对activity和fragment的某些控件的初始化方式可能不同，所以不同情况下需要分别处理
        if (getPresenter().getAreActivity() != null)
        {
            BarUtils.setStatusBarLightMode(getPresenter().getAreActivity(),isLightStyle());
        }
    }

    @Override
    public void initToolbar(String title) {

        initToolbar(title,true);
    }

    @Override
    public void initToolbar(String title, boolean isShowIcon) {
        if (toolbar == null)
            return;

        toolbar.setBackgroundColor(getToolbarBackgroundColor());

        TextView tv_toolTitle = (TextView) toolbar.findViewById(getContext().getResources().getIdentifier("toolTitle", "id", getContext().getPackageName()));
        if (tv_toolTitle == null)
        {
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(getToolbarWidgetColor());
        }
        else
        {
            tv_toolTitle.setText(title);
            tv_toolTitle.setTextColor(getToolbarWidgetColor());
        }

        if (isShowIcon)
        {
            if (getNavIcon() >0 )
                toolbar.setNavigationIcon(getNavIcon());
            else
                toolbar.setNavigationIcon(new BitmapDrawable(ImageUtils.view2Bitmap(new IconFontTextView.Bulder(getContext()).setText(getContext().getResources().getString(R.string.ic_return)).setTextColor(getToolbarWidgetColor()).setTextSize(DensityUtils.dip2px(getContext(),16)).build())));

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppCompatActivity)getContext()).finish();
                }
            });
        }
    }

    @Override
    public String getBehaviorDescription() {
        return "";
    }

    //重写该方法以自定Toolbar图标
    protected int getNavIcon() {
        return 0;
    }

    //重写该方法以自定Toolbar背景色
    protected int getToolbarBackgroundColor(){
        return getContext().getResources().getColor(android.R.color.white);
    }

    //重写该方法以自定Toolbar控件颜色
    protected int getToolbarWidgetColor(){
        return getContext().getResources().getColor(R.color.textcolor_normal);
    }

    //重写该方法以沉浸状态栏
    protected boolean isHideSystemBar(){
        return false;
    }

    //重写该方法以自定Systembar上的字体颜色风格
    protected boolean isLightStyle(){
        return true;
    }

    //隐藏系统栏
    private void hideSystemBar() {
        StatusUtils.setFullToStatusBar((Activity)getContext());
        if (toolbar != null)
            BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
    }

}
