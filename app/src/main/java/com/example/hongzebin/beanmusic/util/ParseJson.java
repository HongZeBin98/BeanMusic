package com.example.hongzebin.beanmusic.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * 解析多层的Json数据
 * Created By Mr.Bean
 */
public class ParseJson {
    /**
     * 通过格式对json数据进行截取
     * @param json 需要截取的原始json数据
     * @param route 截取的格式（a.b.c.d字母是要截取的标签）
     * @return  截取后的json数据
     */
    public static String obtainDesignationJson(String json, String route) {
        // 获取到每一级json字符串的key
        String[] keys = route.split("\\.");
        String newJson = json;
        for (int i = 0; i < keys.length; i++) {
            newJson = splitJsonByKey(newJson, keys[i]);
            if (i == keys.length - 1) {
                return newJson;
            }
        }
        return null;
    }

    /**
     * 通过一个特定标签对json数据进行截取
     * @param json  需要截取的json数据
     * @param key   要截取的标签
     * @return  截取后的json数据
     */
    private static String splitJsonByKey(String json,String key) {
        try {
            JSONObject obj = new JSONObject(json);
            for (Iterator<String> it = obj.keys(); it.hasNext(); ) {
                String s = it.next();
                if (s == null) {
                    return null;
                }
                if (s.equals(key)) {
                    return obj.getString(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
