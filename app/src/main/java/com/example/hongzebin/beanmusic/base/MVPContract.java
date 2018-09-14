package com.example.hongzebin.beanmusic.base;

import com.example.hongzebin.beanmusic.recommendation.bean.ShufflingBean;

import java.util.List;

/**
 * 对view层和presenter层进行规范
 * Created By Mr.Bean
 */
public class MVPContract {

    public interface View{
        void showView(Object response);
    }

    public interface Presenter{
        void getData();
    }
}
