package com.ycu.zzzh.visual_impairment_3zh.utils;

import com.ycu.zzzh.visual_impairment_3zh.mapper.LogsMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.Logs;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName LogServer
 * @description: TODO
 * @Date 2022/5/10 21:26
 * @Version 1.0
 **/
public class LogServer {
    @Autowired
    private LogsMapper logsMapper;

    private void Log(LogLevel level, String message, Object... params){
        //message与params拼接
        String msg=String.format(message,params);
        Logs logs=new Logs();
        logs.setLevel(String.valueOf(level));
        logs.setCreateTime(new Date());
        logsMapper.insert(logs);
        return;
    }

    private void LogInfo(){
        Log(LogLevel.Info,);
    }
    private void LogError(){

    }

    private void LogUserBehavior(){

    }

    private void LogTrack(){
        logTemplate().
    }



     static void logTemplate(){
        static String UserViewNews="%s访问了%s新闻";
    }


    enum LogLevel{
        Error,
        Info,
        Userbehavior,
        Track
    }
}
