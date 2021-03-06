package com.example.hongzebin.beanmusic.base.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 该活动实现了通过EventBus发送底部播放栏状态和接收
 * Created By Mr.Bean
 */
public abstract class BaseEventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册EventBus
        EventBus.getDefault().register(this);
        // 初始化布局
        initView();
        //初始化数据
        initData();
        // 初始化事件
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().postSticky(getConditionStickEvent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    PlayerCondition condition = data.getParcelableExtra("Condition");
                    setConditionStickEvent(condition);
                }
                break;
            default:
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(PlayerCondition condition){
        setConditionStickEvent(condition);
    }

    protected abstract void setConditionStickEvent(PlayerCondition condition);

    protected abstract PlayerCondition getConditionStickEvent();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvents();
}
