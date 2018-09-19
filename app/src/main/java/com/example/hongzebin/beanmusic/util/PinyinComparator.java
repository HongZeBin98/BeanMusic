package com.example.hongzebin.beanmusic.util;

import com.example.hongzebin.beanmusic.locality.bean.MP3Info;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Comparator;

/**
 * 实现对能对英文汉字首字母进行排序
 * Created By Mr.Bean
 */
public class PinyinComparator implements Comparator<MP3Info> {

    @Override
    public int compare(MP3Info o1, MP3Info o2) {
        return o1.getFirstAlphabet().compareTo(o2.getFirstAlphabet());
    }

    /**
     * 获取首汉字拼音的首字母
     * @param str 需要获取首汉字拼音的首字母的字符串
     * @return 首汉字拼音的首字母
     */
    public String getPinYinHeadChar(String str){
        if(str != null){
            char[] strChar = str.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
            //设置输出大写
            outputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            //设置输出不带音标
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            StringBuilder pyStringBuilder = new StringBuilder();
            //通过正则表达式判断是否是中文,否则拿英文的首字母
            if(String.valueOf(strChar[0]).matches("[\\u4E00-\\u9FA5]+")){
                try {
                    String[] pyStringArray = PinyinHelper.toHanyuPinyinStringArray(strChar[0], outputFormat);
                    if (null != pyStringArray && pyStringArray[0] != null) {
                        pyStringBuilder.append(pyStringArray[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else{
                pyStringBuilder.append(str.toUpperCase().charAt(0));
            }
            return pyStringBuilder.toString();
        }
        return null;
    }
}
