package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsContentMapper;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsSortMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.News;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsCondition;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsContent;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsSort;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Override
    //根据id删除新闻
    public Boolean newsRemoveService(String nids) {
        //获取要删除的新闻ID的数组
        String[] nidstr=nids.split(",");
        News news = new News();
       for (int i=0;i<nidstr.length;i++){
           newsMapper.deleteById(Integer.valueOf(nidstr[i]));
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
        //1.创建分页对象存储分页信息
        Page<Object> page1 = PageHelper.startPage(page, rows,false);
        if (newsCondition.getTag()!=""&&newsCondition.getTag()!= null){
            QueryWrapper<NewsSort> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("tag_name",newsCondition.getTag());
            NewsSort sort = newsSortMapper.selectOne(queryWrapper);
            newsCondition.setTag(String.valueOf(sort.getId()));
        }
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
        news.setContentId(newsContent.getId());
        int insert1 = newsContentMapper.insert(newsContent);
        int insert2 = newsMapper.insert(news);
        return true;
    }
    //新闻内容查询
    @Override
    public Map<String, Object> newsContentInfoService(String id) {
        Map<String,Object> map= new HashMap<>();
        //根据id获取新闻数据
        News news = newsMapper.selectById(id);
        //根据新闻数据中的contentId获取新闻内容
        NewsContent newsContent = newsContentMapper.selectById(news.getContentId());
        news.setViewsNum(news.getViewsNum()+1);
        newsMapper.updateById(news);
        //TODO 判断是否有新闻内容与新闻对应，如没有计入日志
        if (newsContent.getContent()==null||newsContent.getContent().equals("")){
            logService.logError("%s的新闻内容不存在",news.getId());
            return null;
        }
        map.put("msg","success");
        map.put("createdTime",news.getCreatedTime() );
        map.put("newsContent",newsContent);

        return map;
    }
}




