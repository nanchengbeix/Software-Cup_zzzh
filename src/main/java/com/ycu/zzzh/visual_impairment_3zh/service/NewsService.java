package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsCondition;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;

import java.util.Map;

/**
* @author 胡富国
* @description 针对表【news】的数据库操作Service
* @createDate 2022-04-19 10:11:39
*/
public interface NewsService extends IService<News> {
    //新闻删除
    Boolean newsRemoveService(String nids);
    //新闻修改
    boolean newsUpdateService(News news);
    //新闻分页查询
    PageResult<NewsResult> newsInfoService(Integer currentPage, Integer pageSize, NewsCondition newsCondition);
    //新闻新增（单项）
    boolean newsAddService(News news);
    //新闻内容查询
    Map<String, Object> newsContentInfoService(String id);
}
