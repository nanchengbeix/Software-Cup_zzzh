package com.ycu.zzzh.visual_impairment_3zh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author 胡富国
* @description 针对表【user(用户信息)】的数据库操作Service
* @createDate 2022-04-09 16:59:55
*/
public interface UserService extends IService<User> {

    //添加用户信息
    int addUserInfoService(User user);
    //根据条件分页查询数据信息
    PageResult<User> selUserInfoService(Integer page, Integer rows, String uname, String status,String phone);
    //根据用户名查询用户信息
    User findByUserName(String username);
    //根据token查询用户信息
    User getUserByToken(String token);
    //根据用户ID获取用户信息
    User personalInfoService(String uid);
    //根据用户ID修改用户信息
    Boolean personalUpdateService(String uid,User user);

//    Msg userLogoutService(HttpServletRequest request);
}
