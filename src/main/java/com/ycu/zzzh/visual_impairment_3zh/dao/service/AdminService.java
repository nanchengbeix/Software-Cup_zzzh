package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.dao.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.dao.domain.PageResult;

/**
* @author 胡富国
* @description 针对表【admin(管理员信息)】的数据库操作Service
* @createDate 2022-04-09 16:29:34
*/
public interface AdminService extends IService<Admin> {

    Admin findByUserName(String username);

    //根据手机号或者姓名查询(手机号与姓名可填)
    PageResult<Admin> selAdminInfoService(Integer page, Integer rows, String aname);
}
