package com.example.hongzebin.beanmusic.music.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 跑马灯TextView，由于TextView要实现跑马灯效果需要获取焦点，因此重写TextView让其一直获取到焦点
 * Created By Mr.Bean
 */
public class MarqueeTextView extends AppCompatTextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {    //重写，且返回值是true，表示始终获取焦点
        return true;
    }
}
