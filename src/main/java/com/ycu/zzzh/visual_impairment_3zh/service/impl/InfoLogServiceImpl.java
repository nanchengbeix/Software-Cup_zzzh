package com.ycu.zzzh.visual_impairment_3zh.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.InfoLog;
import com.ycu.zzzh.visual_impairment_3zh.dao.service.InfoLogService;
import com.ycu.zzzh.visual_impairment_3zh.dao.mapper.InfoLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 胡富国
* @description 针对表【info_log】的数据库操作Service实现
* @createDate 2022-04-13 15:02:59
*/
@Service
public class InfoLogServiceImpl extends ServiceImpl<InfoLogMapper, InfoLog>
    implements InfoLogService{

    @Autowired
    private InfoLogMapper infoLogMapper;

    @Override
    public List<InfoLog> selLogsService() {
        List<InfoLog> list=new ArrayList<InfoLog>();
        list=infoLogMapper.selectList(null);
        return list;
    }
}




