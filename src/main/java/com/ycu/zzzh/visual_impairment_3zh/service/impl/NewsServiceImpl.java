package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsContentMapper;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsCountMapper;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsSortMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.*;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsMapper;
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
    @Override
    //根据id删除新闻
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
    //修改新闻
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

    //新增新闻
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
    //新闻内容查询
    @Override
    public Map<String, Object> newsContentInfoService(String id) {
        Map<String,Object> map= new HashMap<>();
        //根据id获取新闻数据
        News news = newsMapper.selectById(id);
        NewsCount newsCount = newsCountMapper.selectById(news.getId());
        //根据新闻数据中的contentId获取新闻内容
        NewsContent newsContent = newsContentMapper.selectById(news.getContentId());
        newsCount.setViewsNum(newsCount.getViewsNum()+1);
        newsCountMapper.updateById(newsCount);
        //TODO 抛空
        if (newsContent.getContent()==null||newsContent.getContent().equals("")){
            logService.logError("%s的新闻内容不存在",news.getId());
            return null;
        }
        map.put("msg","success");
        map.put("createdTime",news.getCreatedTime() );
        map.put("newsContent",newsContent);
        map.put("rawKeyWords",news.getRawKeyWords());

        return map;
    }
}




