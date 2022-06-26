package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

/**
 * @ClassName NewsLikeController
 * @description: 对用户点赞行为的操作
 * @Date 2022/6/25 15:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("user")
public class NewsLikeController {
    @Autowired
    private UserLikeService userLikeService;

    /**
     * 统计用户点赞的行为
     * @param request
     * @param nid
     * @return
     */
    @RequestMapping(value = {"/userLike"})
    public Msg userLikeNews(ServletRequest request,String nid){
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(request);
        Msg msg=userLikeService.addUserLikeService(uid,nid);
        return  msg;
    }
}
