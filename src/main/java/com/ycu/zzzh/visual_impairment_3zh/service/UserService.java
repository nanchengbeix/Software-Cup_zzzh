package com.ycu.zzzh.visual_impairment_3zh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;

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

    User findByUserName(String username);
}
