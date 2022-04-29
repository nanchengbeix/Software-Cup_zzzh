package com.ycu.zzzh.visual_impairment_3zh.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.Admin;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.dao.service.AdminService;
import com.ycu.zzzh.visual_impairment_3zh.dao.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【admin(管理员信息)】的数据库操作Service实现
* @createDate 2022-04-09 16:29:34
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{
    @Autowired
    private AdminMapper adminMapper;
    //根据用户名获取用户信息
    @Override
    public Admin findByUserName(String aname) {
        //2.根据用户名获取数据库中的用户信息
        //创建一个Querywrapper的对象
        QueryWrapper<Admin> wrapper=new QueryWrapper<>();
        wrapper.eq("aname",aname);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public PageResult<Admin> selAdminInfoService(Integer page, Integer rows, String aname) {
        return null;
    }
}




