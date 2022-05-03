package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.DebugLog;
import com.ycu.zzzh.visual_impairment_3zh.service.DebugLogService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.DebugLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 胡富国
* @description 针对表【debug_log】的数据库操作Service实现
* @createDate 2022-04-13 15:02:53
*/
@Service
public class DebugLogServiceImpl extends ServiceImpl<DebugLogMapper, DebugLog>
    implements DebugLogService{
    @Autowired
    private DebugLogMapper debugLogMapper;
    @Override
    public List<DebugLog> selLogsService() {
        List<DebugLog> list=new ArrayList<DebugLog>();
        list=debugLogMapper.selectList(null);
        return list;
    }
}




