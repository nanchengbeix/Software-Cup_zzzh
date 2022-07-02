package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserAvatarService;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;

import java.io.IOException;

/**
 * @ClassName PersonalManageController
 * @description: TODO
 * @Date 2022/5/28 17:56
 * @Version 1.0
 **/
@RestController
@RequestMapping("/self")
public class PersonalManageController {
    //构造器注入
    private final UserService userService;
    private final UserAvatarService userAvatarService;
    public PersonalManageController(UserService userService,UserAvatarService userAvatarService) {
        this.userService = userService;
        this.userAvatarService=userAvatarService;
    }

    /**
     * 用户查看个人信息
     * TODO 待完善
     * @param request
     * @return
     */
    @RequestMapping( value = "personalInfo",method = {RequestMethod.POST})
    public Msg personalInfo(ServletRequest request){
        Msg<User> userMsg=new Msg<>();
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(request);
        User user=null;
        if(uid!=null&&uid!="") {
            //获取token中的用户id

            user=userService.personalInfoService(uid);
            if (user != null) {
                userMsg.setMsg("获取成功");
                userMsg.setData(user);
            }else {
                userMsg.setErrCode(404);
                userMsg.setMsg("获取失败");
            }
            return userMsg;
        }else {
            userMsg.setErrCode(404);
            userMsg.setMsg("获取失败");
            return userMsg;
        }
    }

    /**
     * 用户修改个人信息
     * @param request 携带Token的请求
     * @param user 用户更改后的信息
     * @return
     */
    @RequestMapping( value = "personalUpdate",method = {RequestMethod.POST})
    public Msg personalUpdate(ServletRequest request,User user){
        Msg<User> userMsg=new Msg<>();
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(request);
        Boolean isSuccess=false;
        if(uid!=null&&uid!="") {
            //获取token中的用户id
            isSuccess=userService.personalUpdateService(uid,user);
            if (isSuccess) {
                userMsg.setMsg("修改成功");
            }else {
                userMsg.setErrCode(404);
                userMsg.setMsg("修改失败");
            }
            return userMsg;
        }else {
            userMsg.setErrCode(404);
            userMsg.setMsg("修改失败");
            return userMsg;
        }
    }

    /**
     * 请求获取用户头像
     * @param request 携带Token的请求
     * @return
     */
    @RequestMapping(value = "photoToBase64",method = {RequestMethod.GET,RequestMethod.POST})
    public Msg<String> photoToBase64(ServletRequest request){
        Msg<String> msg=new Msg<>();
        User user=null;
        String uid=JwtUtils.tokenToId(request);
        if(uid!=null&&uid!="") {
            msg=userAvatarService.photoToBase64(uid);
            return msg;
        }else {
            msg.setErrCode(403);
            msg.setMsg("禁止访问");
            return msg;
        }
    }

    /**
     * 用户设置头像
     * @param photo 用户头像文件流
     * @param request 携带Token的请求
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "userAvatarUpload",method = {RequestMethod.GET,RequestMethod.POST})
    public Msg userAvatarUpload(MultipartFile photo,ServletRequest request) throws IOException {
        Msg msg=new Msg();
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(request);
        if(uid!=null&&uid!="") {
             msg= userAvatarService.userAvatarUploadService(photo,request, uid);
            return msg;
        }else {
            msg.setErrCode(403);
            msg.setMsg("禁止访问");
           return msg;
        }
    }
}
