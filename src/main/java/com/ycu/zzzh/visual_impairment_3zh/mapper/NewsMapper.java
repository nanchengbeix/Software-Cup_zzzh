package com.ycu.zzzh.visual_impairment_3zh.mapper;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsCondition;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡富国
* @description 针对表【news】的数据库操作Mapper
* @createDate 2022-04-29 12:25:00
* @Entity com.ycu.zzzh.visual_impairment_3zh.model.domain.News
*/
public interface NewsMapper extends BaseMapper<News> {
    List<NewsResult> selNewsInfoMapper(@Param("newsCondition") NewsCondition newsCondition);
}




