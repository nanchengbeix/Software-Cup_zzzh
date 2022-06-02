package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpController
 * @description: TODO
 * @Date 2022/4/14 9:19
 * @Version 1.0
 **/
@RestController
@RequestMapping("/http")
public class HttpController {
    static final String URL="http://localhost:8088/userLogin";

    /**
     * 声明单元方法：接收前端请求返回推荐新闻
     * @return
     */
    @RequestMapping("/newsCommend")
    public String newsCommend(){
        //将数据封装成map
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put("username","admin");
        paramMap.put("pwd","123456");
        //将map结构参数，转换为json数据格式发送Http请求
        JSONObject jsonObject = HttpClientUtil.doPostJson(URL, new JSONObject(paramMap));
        Object data = "data";
        String o = (String) jsonObject.get(data);
        System.out.println(jsonObject);
        return o;
    }
}
