package com.ycu.zzzh.visual_impairment_3zh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PageController
 * @description: 公共单元方法只用来完成页面的内部转发
 * @Date 2022/4/7 15:58
 * @Version 1.0
 **/
@RestController
public class PageController {
    /**
     * 声明公共单元方法完成页面的内部转发
     */
    @RequestMapping("{uri}")
    public String getPage(@PathVariable String uri){
        return uri;
    }
}
