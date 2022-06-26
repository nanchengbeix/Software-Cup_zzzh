package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PyRecommendController
 * @description: 从py获取用户推荐新闻id并放入数据库
 * @Date 2022/6/13 18:03
 * @Version 1.0
 **/
@RestController
@RequestMapping("news")
public class PyRecommendController {
    static final String URL="http://[2409:890c:d4a0:2aff:f8d3:9de1:1712:682c]:5000/online";

    /**
     * 声明单元方法：接收前端请求返回推荐新闻
     * @return
     */
    @PostMapping(value = {"/newsCommend"})
    public JSONArray newsCommend(@RequestBody List<Integer> likeLabels){
        //将数据封装成map
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put("rid",likeLabels);
        //将map结构参数，转换为json数据格式发送Http请求
        JSONObject jsonObject = HttpClientUtil.doPostJson(URL, new JSONObject(paramMap));
        Object data = "data";
        JSONArray news_recommend = jsonObject.getJSONArray("news_recommend");
        return news_recommend;
    }
}
