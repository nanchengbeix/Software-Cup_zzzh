package com.ycu.zzzh.visual_impairment_3zh.logs;

import com.ycu.zzzh.visual_impairment_3zh.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName LogService
 * @description: TODO
 * @Date 2022/5/10 21:26
 * @Version 1.0
 **/
@Component
public class LogService {
    @Autowired
    private LogsMapper logsMapper;

    private  void log(LogLevel level, String message, Object... params){
        //message与params拼接
        String msg=String.format(message,params);
        Logs logs=new Logs();
        logs.setLevel(String.valueOf(level));
        logs.setCreateTime(new Date());
        logs.setMessage(msg);
        logsMapper.insert(logs);
        return;
    }

    public  void logInfo(String message, Object... params){
        log(LogLevel.Info, message, params);
    }
    public  void logError(String message, Object... params){

        log(LogLevel.Error, message, params);
    }

    public  void logUserBehavior(String message, Object... params){
        log(LogLevel.Userbehavior, message, params);
    }

    public  void logTrack(String message, Object... params){

        log(LogLevel.Track, message, params);
    }



    enum LogLevel{
        Error,
        Info,
        Userbehavior,
        Track
    }
}
