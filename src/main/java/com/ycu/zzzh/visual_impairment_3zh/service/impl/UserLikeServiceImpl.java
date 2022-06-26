package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.mapper.UserLikeMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserLike;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 胡富国
* @description 针对表【user_like】的数据库操作Service实现
* @createDate 2022-06-26 21:17:25
*/
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike>
    implements UserLikeService{
    @Autowired
    private UserLikeMapper userLikeMapper;

    @Override
    public Msg addUserLikeService(String uid,String nid) {
        Msg msg=new Msg();
        UserLike userLike = new UserLike();
        userLike.setUid(Integer.valueOf(uid));
        userLike.setNid(Integer.valueOf(nid));
        userLike.setCreateTime(new Date());
        userLikeMapper.insert(userLike);
        msg.setMsg("success");
        return msg;
    }
}




