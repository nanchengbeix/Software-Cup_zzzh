package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.model.User;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    //声明service属性
    @Autowired
    private UserService userService;
    //声明单元方法：使用shiro认证
    @PostMapping("userLogin")
    public String userLogin(@RequestBody User user){
        //1.获取subject对象
            Subject subject = SecurityUtils.getSubject();
        System.out.println(user);
        //2.认证
            //创建认证对象存储认证信息
            AuthenticationToken token= new UsernamePasswordToken(user.getUsername(),user.getPwd());
            try{
                subject.login(token);
                return "redirect:main";
            }catch(Exception e){
                e.printStackTrace();
            }
           return "redirect:login";
    }



}
