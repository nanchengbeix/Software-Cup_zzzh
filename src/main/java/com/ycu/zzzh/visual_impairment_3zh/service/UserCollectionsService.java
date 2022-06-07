package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserCollections;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;

/**
* @author 胡富国
* @description 针对表【user_collections】的数据库操作Service
* @createDate 2022-06-02 20:03:50
*/
public interface UserCollectionsService extends IService<UserCollections> {

    Msg collectNewsService(String uid, String nid);

    Msg noCollectNewsService(String uid, String nid);

    PageResult<NewsResult> CollectionInfoService(Integer page, Integer rows,String uid);
}
