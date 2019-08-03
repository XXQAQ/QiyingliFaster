package com.xq.customfaster.base.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xq.androidfaster.base.base.IFasterBaseBehavior;
import com.xq.androidfaster.util.tools.BarUtils;
import com.xq.androidfaster.util.tools.ImageUtils;
import com.xq.androidfaster.util.tools.ResourceUtils;
import com.xq.androidfaster.util.tools.ScreenUtils;
import com.xq.customfaster.R;
import com.xq.customfaster.widget.view.CustomRefreshLoadView;
import com.xq.customview.view.IconFontTextView;
import java.util.ArrayList;
import java.util.List;

public interface ICustomBaseBehavior<T extends ICustomBaseBehavior> extends IFasterBaseBehavior<T> {

    ///////////////////////////////////////////////////////////////////////////
    // P
    ///////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////
    // V
    ///////////////////////////////////////////////////////////////////////////
    public Toolbar getToolbar();

    public ViewGroup getBarLayout();

    public CustomRefreshLoadView getRefreshLoadView();

    //重写该方法以自定Toolbar背景drawable
    default int getBarLayoutBackgroundResource(){
        return R.color.colorWidely;
    }

    //重写该方法以自定Toolbar图标
    default int getToolbarNavIcon() {
        return 0;
    }

    //重写该方法以自定Toolbar控件颜色
    default int getToolbarWidgetColor(){
        return ResourceUtils.getColor(R.color.text_normal);
    }

    //重写该方法以沉浸状态栏(仅在TopContainer有意义)
    default boolean isHideSystemBar(){
        return true;
    }

    //重写该方法以改变状态栏字体颜色(仅在TopContainer有意义)
    default boolean isLightStyle(){
        return true;
    }

    //页面行为描述
    default String getBehaviorDescription(){
        return "";
    }

    default void initToolbar(CharSequence title){
        initToolbar(title,true);
    }

    default void initToolbar(CharSequence title, boolean isShowIcon) {

        if (getToolbar() == null)   return;

        if (!TextUtils.isEmpty(title))
        {
            TextView toolTitle = (TextView) getToolbar().findViewById(getContext().getResources().getIdentifier("toolTitle", "id", getContext().getPackageName()));
            if (toolTitle == null)
            {
                getToolbar().setTitle(title);
                getToolbar().setTitleTextColor(getToolbarWidgetColor());
            } else {
                toolTitle.setText(title);
                toolTitle.setTextColor(getToolbarWidgetColor());
            }
        }

        if (isShowIcon)
        {
            if (getToolbarNavIcon() !=0 )
                getToolbar().setNavigationIcon(getToolbarNavIcon());
            else
                getToolbar().setNavigationIcon(new BitmapDrawable(ImageUtils.view2Bitmap(new IconFontTextView.Bulder(getContext()).setText(ResourceUtils.getString(R.string.ic_return)).setTextColor(getToolbarWidgetColor()).setTextSize(ScreenUtils.dip2px(16)).build())));
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    back();
                }
            });
        }
    }

    default void initCommonView(Object container){
        //BarLayout处理
        initBarLayout();
        //滑动布局嵌套滚动处理
        initNestedScrollingChild();
        //针对顶部容器作特殊处理
        if (isTopContainer(container))
        {
            //隐藏状态栏
            if (isHideSystemBar()) hideSystemBar();
            //设置状态栏字体颜色
            setStatusBarLightMode(isLightStyle());
        }
    }

    default void initBarLayout() {

        if (getBarLayout() == null) return;

        if (getBarLayoutBackgroundResource() != 0)
            getBarLayout().setBackgroundResource(getBarLayoutBackgroundResource());
    }

    default void initNestedScrollingChild(){

        if (getRefreshLoadView() == null)   return;

        List<NestedScrollingChild> list_child = getAllSomeView(getRefreshLoadView(), NestedScrollingChild.class);
        if (list_child == null || list_child.isEmpty()) return;

        for (NestedScrollingChild child : list_child)
            child.setNestedScrollingEnabled(false);
    }

    default void hideSystemBar() {
        BarUtils.setStatusBarColor((Activity) getContext(), Color.TRANSPARENT);
        if (getToolbar() != null)
            BarUtils.addPaddingTopEqualStatusBarHeight(getToolbar());
        else    if (getBarLayout() != null && getBarLayout().getChildCount() > 0)
            //注意：BarLayout的第一个子控件（建议BarLayout只包含一个子控件或BarLayout继承于线性布局）且高度必须是固定值，才能正常处理状态栏高度
            BarUtils.addPaddingTopEqualStatusBarHeight(getBarLayout().getChildAt(0));
    }

    //指定控件具体类型，获取Container容器下所有该类型的控件
    default List getAllSomeView(View container, Class someView) {
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

    //状态栏字体变色
    default void setStatusBarLightMode(boolean isLightStyle){
        BarUtils.setStatusBarLightMode((Activity) getContext(),isLightStyle);
    }

}

