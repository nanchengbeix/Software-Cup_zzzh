package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsContentMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.*;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.ToResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsSortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName NewsManageController
 * @description: TODO
 * @Date 2022/4/19 10:14
 * @Version 1.0
 **/
@RestController
@RequestMapping("news")
public class NewsManageController {
    private final NewsService newsService;
    private final NewsSortService newsSortService;
    private final NewsContentMapper newsContentMapper;
    private static final Logger log = LoggerFactory.getLogger(NewsManageController.class);

    public NewsManageController(NewsService newsService, NewsSortService newsSortService,NewsContentMapper newsContentMapper) {
        this.newsService = newsService;
        this.newsSortService = newsSortService;
        this.newsContentMapper = newsContentMapper;
    }

    /**
     * 新闻删除
     */
    @RequestMapping("newsRemove")
    public ToResult newsRemove(String nids){
        //处理请求
        Boolean result=newsService.newsRemoveService(nids);
        System.out.println("NewsManageController.newsRemove");
        //响应结果
        return new ToResult(result,result?"删除成功":"删除失败");
    }

    /**
     * 新闻修改
     * @return
     */
    @RequestMapping("newsUpdate")
    public ToResult newsUpdate(@RequestBody News news){
        //处理请求
        boolean result=newsService.newsUpdateService(news);
        //响应结果
        return new ToResult(result,result?"修改成功":"修改失败");
    }

    /**
     *新增新闻
     * @param news
     * @return
     */
    @RequestMapping("newsAdd")
    public ToResult newsAdd(@RequestBody News news){
        //处理请求
        boolean result=newsService.newsAddService(news);
        //返回结果
        return new ToResult(result,result?"新增成功":"新增失败");
    }

    /**
     * 新闻查询
     * @param currentPage
     * @param pageSize
     * @param newsCondition
     * @return
     */
    @RequestMapping(value = "newsInfo")
    public PageResult<NewsResult> newsInfo(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            /*@RequestBody*/ NewsCondition newsCondition
    ){
        log.info("newsInfo启动了");
        if (newsCondition.getTag()!=""&&newsCondition.getTag()!= null){
            QueryWrapper<NewsSort> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("tag_name",newsCondition.getTag());
            NewsSort sort = newsSortService.getOne(queryWrapper);
            newsCondition.setTag(String.valueOf(sort.getId()));
        }
        return newsService.newsInfoService(currentPage,pageSize,newsCondition);
    }

    /**
     * 新闻内容查询
     * @param id
     * @return
     */
    @RequestMapping("newsContentInfo")
    public Map<String,Object> newsContentInfo(String id){
        Map<String,Object> map= new HashMap<>();
            //根据id获取新闻数据
            News news = newsService.getById(id);
            //根据新闻数据中的contentId获取新闻内容
            NewsContent newsContent = newsContentMapper.selectById(news.getContentId());
        map.put("msg","success");
        map.put("createdTime",news.getCreatedTime() );
        map.put("newsContent",newsContent);

        return map;
    }






}
