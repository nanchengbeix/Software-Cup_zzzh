package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogsString;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.*;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.ToResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils.AUTH_HEADER;

/**
 * @ClassName NewsController
 * @description: 客户端新闻查询
 * @Date 2022/4/19 10:14
 * @Version 1.0
 **/

@RestController
@RequestMapping("news")
//@CrossOrigin(origins = "http://localhost:8081")
public class NewsController {
    private final NewsService newsService;
    private final LogService logService;
    private static final Logger log = LoggerFactory.getLogger(NewsController.class);

    public NewsController(NewsService newsService, LogService logService) {
        this.newsService = newsService;
        this.logService = logService;
    }

    /**
     * 客户端新闻查询
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
            logService.logUserBehavior(LogsString.UserViewNews,uid,id);
        }
        return newsService.newsContentInfoService(id);
    }






}
