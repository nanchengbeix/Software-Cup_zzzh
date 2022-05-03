package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsContentMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.News;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsCondition;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsContent;
import com.ycu.zzzh.visual_impairment_3zh.model.result.NewsResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import com.ycu.zzzh.visual_impairment_3zh.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //2.调用mapper层完成查询
        System.out.println("NewsServiceImpl.newsInfoService");
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
}




