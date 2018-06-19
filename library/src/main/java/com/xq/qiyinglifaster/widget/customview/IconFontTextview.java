package com.xq.qiyinglifaster.widget.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xq on 2017/7/3.
 */


public class IconFontTextview extends TextView {

    public IconFontTextview(Context context) {
        super(context);
        init(context);
    }

    public IconFontTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconFontTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        setTypeface(iconfont);
    }

    public static class Bulder {

        private Context context;

        private IconFontTextview tv;

        public Bulder(Context context) {
            this.context = context;
            tv = new IconFontTextview(context);
        }

        public Bulder setTextColor(@ColorInt int color) {
            tv.setTextColor(color);
            return this;
        }

        public Bulder setText(CharSequence text) {
            tv.setText(text);
            return this;
        }

        public Bulder setHint(CharSequence text) {
            tv.setHint(text);
            return this;
        }

        public Bulder setTextSize(float size) {
            tv.setTextSize(size);
            return this;
        }

        public Bulder setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
            tv.setAlpha(alpha);
            return this;
        }

        public Bulder setTag(Object tag) {
            tv.setTag(tag);
            return this;
        }

        public Bulder setBackground(Drawable background) {
            tv.setBackground(background);
            return this;
        }

        public Bulder setLayoutParams(ViewGroup.LayoutParams params) {
            tv.setLayoutParams(params);
            return this;
        }

        public IconFontTextview build(){
            return tv;
        }

    }
}