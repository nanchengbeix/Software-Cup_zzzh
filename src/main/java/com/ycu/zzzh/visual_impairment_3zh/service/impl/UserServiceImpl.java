package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.model.User;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-04-07 16:23:22
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        //2.根据用户名获取数据库中的用户信息
        //创建一个Querywrapper的对象
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);

    }
}




