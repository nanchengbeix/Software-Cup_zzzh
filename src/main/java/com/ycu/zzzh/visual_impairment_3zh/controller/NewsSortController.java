package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.model.domain.NewsSort;
import com.ycu.zzzh.visual_impairment_3zh.model.result.SortResult;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName NewsSortController
 * @description: 新闻分类
 * @Date 2022/4/27 16:24
 * @Version 1.0
 **/
@RequestMapping("news")
@RestController
public class NewsSortController {
    private final NewsSortService sortService;

    public NewsSortController(NewsSortService sortService) {
        this.sortService = sortService;
    }

    /**
     * 新闻分类查询
     * @return
     */
    @RequestMapping("newsSortInfo")
    public SortResult<NewsSort> newsSortInfo(){
        List<NewsSort> list = sortService.list();
        return new SortResult<>("success",list.size(),list);

    }
}
