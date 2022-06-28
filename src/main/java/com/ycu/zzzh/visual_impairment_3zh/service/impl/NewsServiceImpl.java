package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.common.constant.UserForNewsStatus;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.*;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.*;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
* @author 胡富国
* @description 针对表【news】的数据库操作Service实现
* @createDate 2022-04-19 10:11:39
*/
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
    implements NewsService{
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsContentMapper newsContentMapper;
    @Autowired
    private NewsSortMapper newsSortMapper;
    @Autowired
    private LogService logService;
    @Autowired
    private NewsCountMapper newsCountMapper;
    @Autowired
    private UserLikeMapper userLikeMapper;
    @Autowired
    private UserCollectionsMapper userCollectionsMapper;

    @Override
    /**
     * 根据id删除新闻
     */
    public Boolean newsRemoveService(String nids) {
        //获取要删除的新闻ID的数组
        String[] nidstr=nids.split(",");
        News news = new News();
       for (int i=0;i<nidstr.length;i++){
           newsMapper.deleteById(Integer.valueOf(nidstr[i]));
           newsCountMapper.deleteById(Integer.valueOf(nidstr[i]));
       }
        return true;
    }

    /**
     * 修改新闻
     * @param news
     * @return
     */
    @Override
    public boolean newsUpdateService(News news) {
        //根据id修改新闻表（一定要传id）
        newsMapper.updateById(news);
        NewsContent newsContent=new NewsContent();

        newsContent.setId(news.getContentId());
        newsContent.setContent(news.getNewsContent().getContent());
        newsContentMapper.updateById(newsContent);
        return true;
    }
    /**
     * 检索新闻
     */
    @Override
    public PageResult<NewsResult> newsInfoService(Integer page, Integer rows, NewsCondition newsCondition) {
        //判断是否是分类查询
        if (newsCondition.getTag()!=""&&newsCondition.getTag()!= null){
            QueryWrapper<NewsSort> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("tag_name",newsCondition.getTag());
            NewsSort sort = newsSortMapper.selectOne(queryWrapper);
            newsCondition.setTag(String.valueOf(sort.getId()));
        }
        //1.创建分页对象存储分页信息
        Page<Object> page1 = PageHelper.startPage(page, rows,false);
        //2.调用mapper层完成查询
       List<NewsResult> newsResults=newsMapper.selNewsInfoMapper(newsCondition);
       //3.将结果封装成PageResult
        PageResult<NewsResult> pageResult=new PageResult<>();
        pageResult.setMsg("success");
        pageResult.setCurrentPage(page1.getPageNum());
        pageResult.setPageSize((long) newsResults.size());
        pageResult.setData(newsResults);
        return pageResult;
    }

    /**
     * 新增新闻
     */
    @Override
    public boolean newsAddService(News news) {
        //获取新闻内容
        NewsContent newsContent =news.getNewsContent();
        //创建新闻统计表对象
        NewsCount newsCount = new NewsCount();
        newsContent.setId(String.valueOf(UUID.randomUUID()));
        news.setContentId(newsContent.getId());
        newsContentMapper.insert(newsContent);
        newsMapper.insert(news);
        QueryWrapper<News> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("content_id",newsContent.getId());
        News newsGetId = newsMapper.selectOne(queryWrapper);
        //TODO 返回错误
        if (newsGetId == null){
            return false;
        }
        newsCount.setNid(newsGetId.getId());
        newsCountMapper.insert(newsCount);
        return true;
    }

    /**
     * 访客新闻内容查询
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> newsContentInfoService(String id) {
        Map<String,Object> map= new HashMap<>();
        //根据id获取新闻数据
        News news = newsMapper.selectById(id);
        //根据新闻id获取新闻统计数据
        NewsCount newsCount = newsCountMapper.selectById(news.getId());
        //根据新闻数据中的contentId获取新闻内容
        NewsContent newsContent = newsContentMapper.selectById(news.getContentId());
        //抛空
        if (newsContent == null){
            logService.logError("%s的新闻内容不存在",news.getId());
            return null;
        }
        newsCount.setViewsNum(newsCount.getViewsNum()+1);
        newsCountMapper.updateById(newsCount);

        map.put("msg","success");
        map.put("createdTime",news.getCreatedTime() );
        map.put("newsContent",newsContent);
        map.put("rawKeyWords",news.getRawKeyWords());
        map.put("sid",news.getTag());

        return map;
    }

    /**
     * 用户新闻内容查询
     * @param uid
     * @param nid
     * @return
     */
    @Override
    public Map<String, Object> newsContentInfoService(String uid, String nid) {
        Map<String,Object> map= new HashMap<>();
        //根据id获取新闻数据
        News news = newsMapper.selectById(nid);
        //根据新闻id获取新闻统计数据
        NewsCount newsCount = newsCountMapper.selectById(news.getId());
        //根据新闻数据中的contentId获取新闻内容
        NewsContent newsContent = newsContentMapper.selectById(news.getContentId());
        //抛空
        if (newsContent == null){
            logService.logError("%s的新闻内容不存在",news.getId());
            return null;
        }
        newsCount.setViewsNum(newsCount.getViewsNum()+1);
        newsCountMapper.updateById(newsCount);
        //查询用户是否收藏此新闻
        QueryWrapper<UserCollections> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",Integer.valueOf(uid)).eq("newid",Integer.valueOf(nid));
        UserCollections userCollections = userCollectionsMapper.selectOne(queryWrapper);
        if (userCollections!=null){
            map.put("isCollect", UserForNewsStatus.COLLECT);
        }else {
            map.put("isCollect", UserForNewsStatus.NOTCOLLECT);
        }
        //查询用户是否点赞此新闻
        QueryWrapper<UserLike> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("uid",Integer.valueOf(uid)).eq("nid",Integer.valueOf(nid));
        UserLike userLike = userLikeMapper.selectOne(queryWrapper2);
        if (userLike != null){
            map.put("isLike",UserForNewsStatus.LIKE);
        }else {
            map.put("isLike",UserForNewsStatus.NOLIKE);
        }

        map.put("msg","success");
        map.put("createdTime",news.getCreatedTime() );
        map.put("newsContent",newsContent);
        map.put("rawKeyWords",news.getRawKeyWords());
        map.put("sid",news.getTag());

        return map;
    }
}




