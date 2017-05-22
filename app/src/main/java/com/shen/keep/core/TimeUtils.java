package com.shen.keep.core;

/**
 * Created by edianzu on 2017/5/22.
 */
public class TimeUtils {

    public static String getTimerStrBySecNum(long  lapseTime){

        StringBuilder result = new StringBuilder();

        int second = (int) Math.floor(lapseTime % 60); // 计算秒
        int minite = (int) Math.floor((lapseTime / 60) % 60); // 计算分
        int day = (int) Math.floor((lapseTime / 3600) / 24); // 计算天
        int hour1 = (int) (Math.floor(lapseTime / 3600) - day * 24); // 计算小时详情页

        if(hour1 < 10){
            result.append("0" + hour1);
        }else {
            result.append(hour1);
        }
        result.append(":");

        if(minite < 10){
            result.append("0" + minite);
        }else {
            result.append(minite);
        }
        result.append(":");

        if(second < 10){
            result.append("0" + second);
        }else {
            result.append(second);
        }

        return  result.toString();
    }
}
