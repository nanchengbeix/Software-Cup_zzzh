package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogsString;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.News;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsCondition;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.ToResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils.AUTH_HEADER;

/**
 * @ClassName NewsManageController
 * @description: 服务端新闻管理
 * @Date 2022/6/12 15:56
 * @Version 1.0
 **/
@RestController
@RequestMapping("newsManage")
public class NewsManageController {
    private final NewsService newsService;
    private final LogService logService;

    public NewsManageController(NewsService newsService, LogService logService) {
        this.newsService = newsService;
        this.logService = logService;
    }

    /**
     * 批量删除
     * @param nids 前端返回的以，分隔的新闻id
     * @return
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
     * TODO 服务端新闻查询具体格式待定
     */


    /**
     * 服务端新闻查询
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
//            //获取token中的用户id
//            String uid = JwtUtils.getUserFiled(token, "uid");
//            //记录用户行为
//            logService.logUserBehavior(LogsString.UserViewNews,uid,id);
        }
        return newsService.newsContentInfoService(id);
    }

}
