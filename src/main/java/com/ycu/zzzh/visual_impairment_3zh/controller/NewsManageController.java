package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogServer;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogsString;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsContentMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.*;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.ToResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsSortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils.AUTH_HEADER;

/**
 * @ClassName NewsManageController
 * @description: TODO
 * @Date 2022/4/19 10:14
 * @Version 1.0
 **/

@RestController
@RequestMapping("news")
//@CrossOrigin(origins = "http://localhost:8081")
public class NewsManageController {
    private final NewsService newsService;
    private final LogServer logServer;
    private static final Logger log = LoggerFactory.getLogger(NewsManageController.class);

    public NewsManageController(NewsService newsService, LogServer logServer) {
        this.newsService = newsService;
        this.logServer = logServer;
    }

    /**
     * 新闻删除
     */
    @RequestMapping("newsRemove")
    public ToResult newsRemove(String nids){
        //处理请求
        Boolean result=newsService.newsRemoveService(nids);
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
        return newsService.newsInfoService(currentPage,pageSize,newsCondition);
    }

    /**
     * 新闻内容查询
     * @param id
     * @return
     */
    @RequestMapping("newsContentInfo")
    public Map<String,Object> newsContentInfo(String id, ServletRequest request) {
        //获取请求头中的token
        HttpServletRequest req= (HttpServletRequest) request;
        String token=req.getHeader(AUTH_HEADER);
        if(token!=null&&token!=""){
            //获取token中的用户id
            String uid = JwtUtils.getUserFiled(token, "uid");
            //记录用户行为
            logServer.logUserBehavior(LogsString.UserViewNews,uid,id);
        }
        return newsService.newsContentInfoService(id);
    }






}
