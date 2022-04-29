package com.ycu.zzzh.visual_impairment_3zh.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.InfoLog;

import java.util.ArrayList;
import java.util.List;

/**
* @author 胡富国
* @description 针对表【info_log】的数据库操作Service
* @createDate 2022-04-13 15:02:59
*/
public interface InfoLogService extends IService<InfoLog> {

    List<InfoLog> selLogsService();
}
