package com.xq.customfaster.widget.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import java.util.HashMap;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class StateLayout extends FrameLayout {

    public static final int STATE_CONTENT = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_ERRO = 4;
    public static final int STATE_EMPTY = 5;

    private Map<Integer, View> stateViewMap = new HashMap<>();

    public StateLayout(@NonNull Context context) {
        super(context);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addStateView(int state,View stateView){
//        第一次添加之前，需要把ContentLayout添加进去
        if (stateViewMap.isEmpty() && getChildCount() > 0)
        {
            stateViewMap.put(STATE_CONTENT,getChildAt(0));
        }
        stateView.setVisibility(GONE);
        addView(stateView,MATCH_PARENT,MATCH_PARENT);
        stateViewMap.put(state,stateView);
    }

    public View getStateView(int state){
        return stateViewMap.get(state);
    }

    public void showStateView(int state){
        if (stateViewMap.containsKey(state))
        {
            for (Map.Entry<Integer,View> entry : stateViewMap.entrySet())
            {
                if (entry.getKey() == state)
                    entry.getValue().setVisibility(VISIBLE);
                else
                    entry.getValue().setVisibility(GONE);
            }
        }
    }

}
