package com.ycu.zzzh.visual_impairment_3zh.utils;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {
    /**
     *  获取系统时间并且转换为能存入MySQL的datetime的格式
     * @return timestamp（能存入MySQL）
     */
    public static Timestamp nowDateTime(){
        //获取系统当前时间，格式：Tue Jun 23 19:57:59 CST 2020
        Date date = new Date();
        //获取系统当前时间的时间戳，格式：1592913479942
        long dataTime = date.getTime();
        //把时间戳转换为能够存入MySQL的datetime的时间格式
        Timestamp timestamp = new Timestamp(dataTime);
        return timestamp;
    }
}

