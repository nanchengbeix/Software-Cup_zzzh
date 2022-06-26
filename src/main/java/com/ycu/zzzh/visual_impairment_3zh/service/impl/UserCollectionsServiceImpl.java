package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserCollections;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.service.UserCollectionsService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.UserCollectionsMapper;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public PageResult<NewsResult> CollectionInfoService(Integer page, Integer rows,String uid) {
        //1.创建分页对象存储分页信息
        Page<Object> page1 = PageHelper.startPage(page, rows,false);
        //2.调用mapper层完成查询
        List<NewsResult> newsResults=userCollectionsMapper.selColNewsInfoMapper(uid);
        PageResult<NewsResult> pageResult=new PageResult<>();
        pageResult.setMsg("success");
        pageResult.setCurrentPage(page1.getPageNum());
        pageResult.setPageSize((long) newsResults.size());
        pageResult.setData(newsResults);
        return pageResult;
    }
}




