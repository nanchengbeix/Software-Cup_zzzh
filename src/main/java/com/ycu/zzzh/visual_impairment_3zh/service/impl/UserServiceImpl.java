package com.ycu.zzzh.visual_impairment_3zh.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.dao.mapper.UserMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.ycu.zzzh.visual_impairment_3zh.dao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
* @author 胡富国
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-04-09 16:59:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    //声明mapper层属性
    @Autowired
    private UserMapper userMapper;

    //根据条件分页查询用户信息
    @Override
    public PageResult<User> selUserInfoService(Integer page, Integer rows, String username, String status,String phone) {
        //1.创建分页对象存储分页信息
        Page<Object> page1 = PageHelper.startPage(page, rows);
        //根据条件查询
        //创建条件构造器
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (username!=null&&!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if (status!=null&&!"".equals(status)){
            queryWrapper.eq("status",status);
        }
        if (phone!=null&&!"".equals(phone)){
            queryWrapper.eq("phone",phone);
        }
        List<User> list = userMapper.selectList(queryWrapper);
        PageResult<User> pageResult=new PageResult<User>((long) list.size(),list);
        return pageResult;
    }

    public int addUserInfoService(User user) {
        //添加创建时间
        user.setCreateTime(LocalDate.now());
        user.setStatus("1");
        return userMapper.insert(user);

    }
}




