package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.service.UserCollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

/**
 * @ClassName CollectionsController
 * @description: TODO
 * @Date 2022/6/2 19:58
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = {"collection"},method = {RequestMethod.GET,RequestMethod.POST})
public class CollectionsController {
    private final UserCollectionsService userCollectionsService;

    public CollectionsController(UserCollectionsService userCollectionsService) {
        this.userCollectionsService = userCollectionsService;
    }

    /**
     * 用户收藏新闻
     * @param nid 新闻id
     * @param request 携带Token的请求
     * @return
     */
    @RequestMapping(value = {"collectNews"},method = {RequestMethod.POST,RequestMethod.GET})
    public Msg collectNews(String nid, ServletRequest request){
        Msg msg=new Msg();
        String uid= JwtUtils.tokenToId(request);
        if(uid!=null&&uid!="") {
            msg=userCollectionsService.collectNewsService(uid,nid);
            return msg;
        }else {
            msg.setErrCode(403);
            msg.setMsg("禁止访问");
            return msg;
        }

    }

    /**
     * 用户取消收藏新闻
     * @param nid 新闻id
     * @param request 携带Token的请求
     * @return
     */
    @RequestMapping(value = {"noCollectNews"},method = {RequestMethod.POST,RequestMethod.GET})
    public Msg noCollectNews(String nid, ServletRequest request){
        Msg msg=new Msg();
        String uid= JwtUtils.tokenToId(request);
        if(uid!=null&&uid!="") {
            msg=userCollectionsService.noCollectNewsService(uid,nid);
            return msg;
        }else {
            msg.setErrCode(403);
            msg.setMsg("禁止访问");
            return msg;
        }

    }

    /**
     * 用户查看收藏新闻
     * @param currentPage
     * @param pageSize
     * @param request 携带Token的请求
     * @return
     */
    @RequestMapping(value = {"collectionInfo"},method = {RequestMethod.POST,RequestMethod.GET})
    public PageResult<NewsResult> collectionInfo(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                                 ServletRequest request){
        PageResult<NewsResult> pageResult=new PageResult<>();
        String uid= JwtUtils.tokenToId(request);
        if(uid!=null&&uid!="") {
            pageResult=userCollectionsService.CollectionInfoService(currentPage,pageSize,uid);
            return pageResult;
        }else {
            pageResult.setMsg("禁止访问");
            return pageResult;
        }
    }
}
