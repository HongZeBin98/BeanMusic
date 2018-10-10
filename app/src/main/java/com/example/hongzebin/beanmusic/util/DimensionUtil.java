package com.example.hongzebin.beanmusic.util;

public class DimensionUtil {
    public static int dip2px(float dpValue) {
        float scale = BeanMusicApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
