package com.xq.qiyinglifaster.widget.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2017/7/3.
 */


public class IconFontButton extends Button {

    public IconFontButton(Context context) {
        super(context);
        init(context);
    }

    public IconFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
       setTypeface(iconfont);
    }

    public static class Bulder {

        private Context context;

        private IconFontButton bt;

        public Bulder(Context context) {
            this.context = context;
            bt = new IconFontButton(context);
        }

        public Bulder setTextColor(@ColorInt int color) {
            bt.setTextColor(color);
            return this;
        }

        public Bulder setText(CharSequence text) {
            bt.setText(text);
            return this;
        }

        public Bulder setHint(CharSequence text) {
            bt.setHint(text);
            return this;
        }

        public Bulder setTextSize(float size) {
            bt.setTextSize(size);
            return this;
        }

        public Bulder setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
            bt.setAlpha(alpha);
            return this;
        }

        public Bulder setTag(Object tag) {
            bt.setTag(tag);
            return this;
        }

        public Bulder setBackground(Drawable background) {
            bt.setBackground(background);
            return this;
        }

        public Bulder setLayoutParams(ViewGroup.LayoutParams params) {
            bt.setLayoutParams(params);
            return this;
        }

        public IconFontButton build(){
            return bt;
        }

    }
}