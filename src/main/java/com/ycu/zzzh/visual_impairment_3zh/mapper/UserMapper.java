package com.ycu.zzzh.visual_impairment_3zh.mapper;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;

/**
* @author 胡富国
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-04-25 11:13:09
* @Entity com.ycu.zzzh.visual_impairment_3zh.model.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    Integer updPersonalInfoMapper(String username, String phone, Date birth, String site,
                                  String modifier,Date lastmodifidTime,String uid);
}




