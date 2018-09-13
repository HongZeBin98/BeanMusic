package com.example.hongzebin.beanmusic.recommendation.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hongzebin.beanmusic.recommendation.bean.ShufflingBean;
import com.example.hongzebin.beanmusic.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecommendationModel {

    public interface RecModelCallback{
        void onFinish(List<ShufflingBean> shufflingBeans);
    }

    /**
     * 从网上获取到轮播图实体类
     * @param shufflingCount 需要获取的轮播图的数量
     * @param callback  回调接口
     */
    public void getShufflings(int shufflingCount, final RecModelCallback callback){
        HttpUtil.sendHttpRequest(MusicApi.focusPic(shufflingCount), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("IOException", Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                parseJson(responseData, callback);
            }
        });
    }

    /**
     * 通过Gson和JSONObject相结合解析json数据
     * @param jsonData 需要解析的json数据
     * @param callback  回调接口
     */
    private void parseJson(String jsonData ,RecModelCallback callback){
        try {
            Gson gson = new Gson();
            jsonData = (String) new JSONObject(jsonData).get("pic");
            List<ShufflingBean> shuffling = gson.fromJson(jsonData, new TypeToken<List<ShufflingBean>>(){}.getType());
            callback.onFinish(shuffling);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
