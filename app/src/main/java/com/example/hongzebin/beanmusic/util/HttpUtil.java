package com.example.hongzebin.beanmusic.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 通过okHttp进行网络获取数据
 * Created By Mr.Bean
 */
public class HttpUtil {

    /**
     * 发送网络请求获取数据
     * @param address 请求网址
     * @param callback  接口
     */
    public static void sendHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
