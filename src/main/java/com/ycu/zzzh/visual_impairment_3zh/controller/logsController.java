package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.DebugLog;
import com.ycu.zzzh.visual_impairment_3zh.service.DebugLogService;
import com.ycu.zzzh.visual_impairment_3zh.service.InfoLogService;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.InfoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName logsController
 * @description: 对日志进行相关操作
 * @Date 2022/4/12 21:51
 * @Version 1.0
 **/
@RequestMapping("/logs")
@RestController
public class logsController {
    @Autowired
    private InfoLogService infoLogService;
    @Autowired
    private DebugLogService debugLogService;

    /**
     * 声明单元方法：提取数据库中Info级别的日志信息
     * @return
     */
    @RequestMapping("/selInfoLogs")
    public List<InfoLog> selInfoLogs(){
        List<InfoLog> infoLogs=infoLogService.selLogsService();
        return infoLogs;
    }

    /**
     * 声明单元方法：提取数据库中SQL语句的日志信息
     * @return
     */
    @RequestMapping("/selSqlLogs")
    public List<DebugLog> selSqlLogs(){
        List<DebugLog> debugLogs=debugLogService.selLogsService();
        return debugLogs;
    }


}
