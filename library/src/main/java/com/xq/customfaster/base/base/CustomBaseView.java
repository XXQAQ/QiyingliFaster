package com.xq.customfaster.base.base;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.xq.customview.view.IconFontTextView;
import com.xq.projectdefine.util.tools.BarUtils;
import com.xq.projectdefine.util.tools.ImageUtils;
import com.xq.projectdefine.util.tools.ScreenUtils;
import com.xq.customfaster.R;
import com.xq.projectdefine.base.base.FasterBaseView;

public abstract class CustomBaseView<T extends ICustomBasePresenter> extends FasterBaseView<T> implements ICustomBaseView<T> {

    protected Toolbar toolbar;

    protected NestedScrollView sv;

    public CustomBaseView(T presenter) {
        super(presenter);
    }

    @Override
    public void afterOnCreate(Bundle savedInstanceState) {
        super.afterOnCreate(savedInstanceState);

        if (sv != null)
            sv.setNestedScrollingEnabled(false);

        //针对顶部容器作特殊处理
        if (isTopContainer())
        {
            //隐藏状态栏
            if (isHideSystemBar()) hideSystemBar();
            //设置状态栏字体颜色
            setStatusBarLightMode(isLightStyle());
        }

        //针对activity和fragment的某些控件的初始化方式可能不同，所以不同情况下需要分别处理
        if (getPresenter().getAreActivity() != null)
        {

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
            if (getNavIcon() !=0 )
                toolbar.setNavigationIcon(getNavIcon());
            else
                toolbar.setNavigationIcon(new BitmapDrawable(ImageUtils.view2Bitmap(new IconFontTextView.Bulder(getContext()).setText(getContext().getResources().getString(R.string.ic_return)).setTextColor(getToolbarWidgetColor()).setTextSize(ScreenUtils.dip2px(getContext(),16)).build())));

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
        return getContext().getResources().getColor(R.color.colorWidely);
    }

    //重写该方法以自定Toolbar控件颜色
    protected int getToolbarWidgetColor(){
        return getContext().getResources().getColor(R.color.text_normal);
    }

    //重写该方法以沉浸状态栏(仅在TopContainer有意义)
    protected boolean isHideSystemBar(){
        return true;
    }

    //重写该方法以改变状态栏字体颜色
    protected boolean isLightStyle(){
        return true;
    }

    private void setStatusBarLightMode(boolean isLightStyle){
        BarUtils.setStatusBarLightMode(getPresenter().getAreActivity(),isLightStyle);
    }

    private void hideSystemBar() {
        BarUtils.setStatusBarFull((Activity) getContext());
        if (toolbar != null)
            BarUtils.addHeightEqualStatusBarHeight(toolbar);
    }

}
