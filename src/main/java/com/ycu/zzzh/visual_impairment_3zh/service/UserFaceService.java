package com.ycu.zzzh.visual_impairment_3zh.service;

import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserFace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;

/**
* @author 胡富国
* @description 针对表【user_face(用户人脸表)】的数据库操作Service
* @createDate 2022-06-10 17:00:47
*/
public interface UserFaceService extends IService<UserFace> {

    Msg addUserFaceService(JSONObject response);
}
