package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.UserMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.DateUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private final LogService logService;

    public UserServiceImpl(LogService logService){
        this.logService = logService;
    }

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
        PageResult<User> pageResult=new PageResult<User>();
        pageResult.setCurrentPage(page1.getPageNum());
        pageResult.setPageSize((long) list.size());
        pageResult.setData(list);
        return pageResult;
    }

    //根据用户名获取用户信息
    @Override
    public User findByUserName(String username) {
        //根据用户名获取数据库的用户信息
            //创建一个QueryWrapper
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("username",username);
            return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User getUserByToken(String token) {
        return null;
    }

    //客户端：id获取用户信息
    @Override
    public User personalInfoService(String uid) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.select("username","phone","birth","site").eq("uid",uid);
        User user = userMapper.selectOne(wrapper);
//        Date date = DateUtil.dateLongToDateShort(user.getBirth());
//        try {
//            Date nowDateShort = DateUtil.getNowDateShort();
//        } catch (ParseException e) {
//            logServer.logError("%s获取短时间格式出现错误：%s",personalInfoService(null).getClass().getSimpleName(),e);
//        }
//        user.setBirth(date);
        return user;



    }

    //客户端：根据id修改用户信息
    @Override
    public Boolean personalUpdateService(String uid,User user) {
        user.setModifier("self");
        user.setLastmodifidTime(DateUtil.getNowDate());
        Integer i = userMapper.updPersonalInfoMapper(user.getUsername(), user.getPhone(), user.getBirth(), user.getSite()
                                                    ,user.getModifier(),user.getLastmodifidTime(),uid);
        if (i!= 0){
            return true;
        }else {
            return false;
        }
    }

//    @Override
//    public Msg userLogoutService(HttpServletRequest request) {
//        Msg msg=new Msg();
//        //移除token
//
//        return msg;
//    }

    //增加用户信息
    @Override
    public int addUserInfoService(User user) {
        //添加创建时间
        Date date=new Date();
        user.setCreateTime(date);
        user.setStatus("0");
        SimpleHash simpleHash= new SimpleHash( "SHA-1", user.getPwd(), user.getUsername(), 16);
        user.setPwd(simpleHash.toString());
        return userMapper.insert(user);

    }
}




