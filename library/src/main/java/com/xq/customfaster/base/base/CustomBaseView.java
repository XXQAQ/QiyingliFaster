package com.xq.customfaster.base.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xq.androidfaster.base.base.aspresenter.FasterBaseView;
import com.xq.androidfaster.util.tools.BarUtils;
import com.xq.androidfaster.util.tools.ImageUtils;
import com.xq.androidfaster.util.tools.ResourceUtils;
import com.xq.androidfaster.util.tools.ScreenUtils;
import com.xq.customfaster.widget.view.CustomRefreshLoadView;
import com.xq.customview.view.IconFontTextView;
import com.xq.customfaster.R;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomBaseView<T extends ICustomBasePresenter> extends FasterBaseView<T> implements ICustomBaseView<T> {

    protected ViewGroup barLayout;
    protected Toolbar toolbar;

    public CustomBaseView(T presenter) {
        super(presenter);
    }

    @Override
    public void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);

        initBarLayout();

        initNestedScrollingChild();

        //针对顶部容器作特殊处理
        if (isTopContainer())
        {
            //隐藏状态栏
            if (isHideSystemBar()) hideSystemBar();
            //设置状态栏字体颜色
            setStatusBarLightMode(isLightStyle());
        }

    }

    @Override
    public void initToolbar(CharSequence title) {
        initToolbar(title,true);
    }

    @Override
    public void initToolbar(CharSequence title, boolean isShowIcon) {

        if (toolbar == null)
        {
            List<Toolbar> list = getAllSomeView(getRootView(), Toolbar.class);
            if (list == null || list.isEmpty()) return;
            toolbar = list.get(0);
        }

        if (!TextUtils.isEmpty(title))
        {
            TextView toolTitle = (TextView) toolbar.findViewById(getContext().getResources().getIdentifier("toolTitle", "id", getContext().getPackageName()));
            if (toolTitle == null)
            {
                toolbar.setTitle(title);
                toolbar.setTitleTextColor(getToolbarWidgetColor());
            }
            else
            {
                toolTitle.setText(title);
                toolTitle.setTextColor(getToolbarWidgetColor());
            }
        }

        if (isShowIcon)
        {
            if (getToolbarNavIcon() !=0 )
                toolbar.setNavigationIcon(getToolbarNavIcon());
            else
                toolbar.setNavigationIcon(new BitmapDrawable(ImageUtils.view2Bitmap(new IconFontTextView.Bulder(getContext()).setText(ResourceUtils.getString(R.string.ic_return)).setTextColor(getToolbarWidgetColor()).setTextSize(ScreenUtils.dip2px(16)).build())));

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    back();
                }
            });
        }
    }

    @Override
    public String getBehaviorDescription() {
        return "";
    }

    protected void initBarLayout() {

        if (barLayout == null)
        {
            List<ViewGroup> list = getAllSomeView(getRootView(), AppBarLayout.class);
            if (list != null && !list.isEmpty()) barLayout = list.get(0);
        }

        if (barLayout == null) return;

        if (getBarLayoutBackgroundResource() != 0)
            barLayout.setBackgroundResource(getBarLayoutBackgroundResource());
    }

    protected void initNestedScrollingChild(){

        CustomRefreshLoadView refreshLoadView;

        List<CustomRefreshLoadView> list_refreshLoadView = getAllSomeView(getRootView(), CustomRefreshLoadView.class);
        if (list_refreshLoadView == null || list_refreshLoadView.isEmpty()) return;
        refreshLoadView = list_refreshLoadView.get(0);

        List<NestedScrollingChild> list_child = getAllSomeView(refreshLoadView, NestedScrollingChild.class);
        if (list_child == null || list_child.isEmpty()) return;
        for (NestedScrollingChild child : list_child)
            child.setNestedScrollingEnabled(false);
    }

    //重写该方法以自定Toolbar背景drawable
    protected int getBarLayoutBackgroundResource(){
        return R.color.colorWidely;
    }

    //重写该方法以自定Toolbar图标
    protected int getToolbarNavIcon() {
        return 0;
    }

    //重写该方法以自定Toolbar控件颜色
    protected int getToolbarWidgetColor(){
        return ResourceUtils.getColor(R.color.text_normal);
    }

    //重写该方法以沉浸状态栏(仅在TopContainer有意义)
    protected boolean isHideSystemBar(){
        return true;
    }

    //重写该方法以改变状态栏字体颜色(仅在TopContainer有意义)
    protected boolean isLightStyle(){
        return true;
    }

    //指定控件具体类型，获取Container容器下所有该类型的控件
    protected List getAllSomeView(View container, Class someView) {
        List list = new ArrayList<>();
        if (container instanceof ViewGroup)
        {
            ViewGroup viewGroup = (ViewGroup) container;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View view = viewGroup.getChildAt(i);
                if (someView.isAssignableFrom(view.getClass()))
                    list.add(view);
                //再次 调用本身（递归）
                list.addAll(getAllSomeView(view,someView));
            }
        }
        return list;
    }

    private void setStatusBarLightMode(boolean isLightStyle){
        BarUtils.setStatusBarLightMode((Activity) getContext(),isLightStyle);
    }

    private void hideSystemBar() {
        BarUtils.setStatusBarColor((Activity) getContext(), Color.TRANSPARENT);

        if (toolbar != null)
            BarUtils.addPaddingTopEqualStatusBarHeight(toolbar);
        else    if (barLayout != null && barLayout.getChildCount() > 0)         //注意：BarLayout的第一个子控件（建议BarLayout只包含一个子控件或BarLayout继承于线性布局）高度必须是固定值，才能正常处理状态栏高度
            BarUtils.addPaddingTopEqualStatusBarHeight(barLayout.getChildAt(0));
    }

}
