package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserLike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;

/**
* @author 胡富国
* @description 针对表【user_like】的数据库操作Service
* @createDate 2022-06-26 21:17:25
*/
public interface UserLikeService extends IService<UserLike> {

    Msg addUserLikeService(String uid,String nid);
}
