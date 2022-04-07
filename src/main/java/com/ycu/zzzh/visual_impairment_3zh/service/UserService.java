package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡富国
* @description 针对表【user】的数据库操作Service
* @createDate 2022-04-07 16:23:22
*/
public interface UserService extends IService<User> {
    public User findByUserName(String username);
}
