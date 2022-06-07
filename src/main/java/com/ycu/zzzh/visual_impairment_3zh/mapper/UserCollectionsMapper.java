package com.ycu.zzzh.visual_impairment_3zh.mapper;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserCollections;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;

import java.util.List;

/**
* @author 胡富国
* @description 针对表【user_collections】的数据库操作Mapper
* @createDate 2022-06-02 20:03:50
* @Entity com.ycu.zzzh.visual_impairment_3zh.model.domain.UserCollections
*/
public interface UserCollectionsMapper extends BaseMapper<UserCollections> {

    List<NewsResult> selColNewsInfoMapper(String uid);
}




