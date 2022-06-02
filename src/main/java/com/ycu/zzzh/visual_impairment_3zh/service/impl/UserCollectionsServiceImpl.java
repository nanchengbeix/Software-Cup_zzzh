package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserCollections;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserCollectionsService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.UserCollectionsMapper;
import com.ycu.zzzh.visual_impairment_3zh.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 胡富国
* @description 针对表【user_collections】的数据库操作Service实现
* @createDate 2022-06-02 20:03:50
*/
@Service
public class UserCollectionsServiceImpl extends ServiceImpl<UserCollectionsMapper, UserCollections>
    implements UserCollectionsService{
    @Autowired
    private UserCollectionsMapper userCollectionsMapper;

    @Override
    public Msg collectNewsService(String uid, String nid) {
        Msg msg=new Msg();
        Date nowDate = DateUtil.getNowDate();
        UserCollections userCollections=new UserCollections();
        userCollections.setUserid(Long.valueOf(uid));
        userCollections.setNewid(Long.valueOf(nid));
        userCollections.setCurtime(nowDate);
        int insert = userCollectionsMapper.insert(userCollections);
        if (insert==1){
            msg.setMsg("收藏成功");
            return msg;
        }else {
            msg.setErrCode(500);
            msg.setMsg("收藏失败");
            return msg;
        }

    }

    @Override
    public Msg noCollectNewsService(String uid, String nid) {
        Msg msg=new Msg();
        QueryWrapper<UserCollections> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",uid).eq("newid",nid);
        userCollectionsMapper.delete(queryWrapper);
        msg.setMsg("取消收藏成功");
        return msg;
    }
}




