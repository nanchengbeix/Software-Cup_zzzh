package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserFace;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserFaceService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.UserFaceMapper;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【user_face(用户人脸表)】的数据库操作Service实现
* @createDate 2022-06-10 17:00:47
*/
@Service
public class UserFaceServiceImpl extends ServiceImpl<UserFaceMapper, UserFace>
    implements UserFaceService{

    @Autowired
    private UserFaceMapper userFaceMapper;
    @Override
    public Msg addUserFaceService(JSONObject response) {
        JSONObject body=response.getJSONObject("body");
        UserFace userFace=new UserFace();
        userFace.setId(body.getString("id"));
        userFace.setUid(body.getInteger("name"));
        userFace.setImageIds(String.valueOf(body.getJSONArray("imageIds")));
        userFace.setCreateTime(DateUtil.nowDateTime());
        userFaceMapper.insert(userFace);
        Msg msg=new Msg();
        msg.setMsg("录入成功");
        return msg;
    }
}




