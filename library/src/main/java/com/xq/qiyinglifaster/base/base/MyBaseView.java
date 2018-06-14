package com.xq.qiyinglifaster.base.base;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import com.xq.projectdefine.util.tools.ViewUtils;
import com.xq.qiyinglifaster.R;
import com.xq.projectdefine.base.base.FasterBaseView;
import com.yanzhenjie.statusview.StatusUtils;

public abstract class MyBaseView<T extends IMyBasePresenter> extends FasterBaseView<T> implements IMyBaseView<T> {

    protected NestedScrollView sv;

    public MyBaseView(T presenter) {
        super(presenter);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        sv = (NestedScrollView)findViewById(getContext().getResources().getIdentifier("sv", "id", getContext().getPackageName()));
        if (sv != null)
            sv.setNestedScrollingEnabled(false);

        if (isHideSystemBar())
            hideSystemBar();

        //TODO:针对activity和fragment的某些控件的初始化方式可能不同，所以不同情况下需要分别处理
        if (getPresenter().getAreActivity() != null)
        {
            if (isLightStyle())
                statusBarDarkFont();
        }
    }

    @Override
    public int getNavIcon() {
        return R.mipmap.refresh_loading01;
    }

    @Override
    public int getToolbarBackgroundColor(){
        return getContext().getResources().getColor(android.R.color.white);
    }

    @Override
    public int getToolbarWidgetColor(){
        return getContext().getResources().getColor(R.color.textcolor_normal);
    }

    @Override
    public String getBehaviorDescription() {
        return "";
    }

    //重写该方法以沉浸状态栏
    protected boolean isHideSystemBar(){
        return false;
    }

    //重写该方法以自定Systembar上的字体颜色风格
    protected boolean isLightStyle(){
        return true;
    }

    //修改状态栏字体颜色为黑色
    private void statusBarDarkFont(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            ((Activity)getContext()).getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    //隐藏系统栏
    private void hideSystemBar() {
        StatusUtils.setFullToStatusBar((Activity)getContext());
        if (toolbar != null)
        {
            setHeightAndPadding(getContext(), toolbar);
        }
    }

    private void setHeightAndPadding(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.height += ViewUtils.getStatusBarHeight(context);//增高
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + ViewUtils.getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }
}
