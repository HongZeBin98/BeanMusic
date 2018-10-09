package com.example.hongzebin.beanmusic.music.model;

import android.util.Log;

import com.example.hongzebin.beanmusic.music.bean.LrcBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicModel {

    public interface DownLoadCallBack {
        void onFinish(List<LrcBean> lrcBeans);
        void onFailure(Exception e);
    }

    public void downLoadLrc(final String lrcUrl, final DownLoadCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                Reader read;
                BufferedReader bufferReader;
                try {
                    URL url = new URL(lrcUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    read = new InputStreamReader(connection.getInputStream());
                    bufferReader = new BufferedReader(read);                        //获取服务器返回的字符串
                    String str; //读取每一行数据
                    StringBuilder strBuilder = new StringBuilder();  //接受全部数据
                    while ((str = bufferReader.readLine()) != null) {
                        strBuilder.append(str).append("\n");
                    }
                    //关闭连接
                    read.close();
                    connection.disconnect();
                    //测试
                    Log.e("读取来的数据", strBuilder.toString());
                    List<LrcBean> list = analyseLrc(strBuilder.toString());
                    callBack.onFinish(list);
                } catch (Exception e) {
                    callBack.onFailure(e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 解析lrc歌词成歌词实体类
     * @param rawLrc lrc歌词
     * @return 歌词实体类
     */
    private List<LrcBean> analyseLrc(String rawLrc) {
        if (rawLrc == null || rawLrc.length() == 0) {
            return null;
        }
        StringReader reader = new StringReader(rawLrc);
        BufferedReader br = new BufferedReader(reader);
        String line;
        List<LrcBean> lrcBeanList = new ArrayList<>();
        try {
            //循环地读取歌词的每一行
            do {
                line = br.readLine();
                if (line != null && line.length() > 0) {
                    //解析每一行歌词 得到每行歌词的集合，因为有些歌词重复有多个时间，就可以解析出多个歌词行来
                    List<LrcBean> lrcRows = analyseLrcLine(line);
                    if (lrcRows != null && lrcRows.size() > 0) {
                        lrcBeanList.addAll(lrcRows);
                    }
                }
            } while (line != null);

            if (lrcBeanList.size() > 0) {
                // 根据歌词行的时间排序
                Collections.sort(lrcBeanList);
                if (lrcBeanList.size() > 0) {
                    for (LrcBean lrcRow : lrcBeanList) {
                        Log.d("analyse Finish", "lrcRow:" + lrcRow.toString());
                    }
                }
            }
        } catch (Exception e) {
            Log.e("MusicModel", Log.getStackTraceString(e));
            return null;
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();
        }
        return lrcBeanList;
    }

    /**
     * 解析lrc中每一行的歌词信息
     *
     * @param lrcLine 一行歌词信息
     * @return 歌词实体类
     */
    private List<LrcBean> analyseLrcLine(String lrcLine) {
        if (lrcLine.indexOf("[") != 0 || lrcLine.indexOf(".") != 6 || lrcLine.indexOf("o") == 1) {
            return null;
        }
        //找到最后一个“]”的位置
        int lastIndexOfRight = lrcLine.lastIndexOf("]");
        //获取歌词内容
        String context = lrcLine.substring(lastIndexOfRight + 1, lrcLine.length());
        //获取“]”之前的时间
        String time = lrcLine.substring(0, lastIndexOfRight + 1)
                .replace("[", "-").replace("]", "-");
        String arrTimes[] = time.split("-");
        List<LrcBean> lrcLineList = new ArrayList<>();
        for (String item : arrTimes) {
            if (item.trim().length() == 0) {
                continue;
            }
            LrcBean lrcBean = new LrcBean(item, timeConvert(item), context);
            lrcLineList.add(lrcBean);
        }
        return lrcLineList;
    }

    /**
     * 把lrc中的时间转换成毫秒数
     *
     * @param timeStr 原本lrc中的时间
     * @return 毫秒时间
     */
    private long timeConvert(String timeStr) {
        //将字符串 XX:XX.XX 转换为 XX:XX:XX
        timeStr = timeStr.replace('.', ':');
        //将字符串 XX:XX:XX 拆分
        String[] times = timeStr.split(":");
        return Integer.valueOf(times[0]) * 60 * 1000 +//分
                Integer.valueOf(times[1]) * 1000 +//秒
                Integer.valueOf(times[2]);//毫秒
    }
}
