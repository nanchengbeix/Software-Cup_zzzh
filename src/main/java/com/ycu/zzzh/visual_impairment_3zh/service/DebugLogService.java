package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.DebugLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 胡富国
* @description 针对表【debug_log】的数据库操作Service
* @createDate 2022-04-13 15:02:53
*/
public interface DebugLogService extends IService<DebugLog> {


    List<DebugLog> selLogsService();
}
