package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName VoiceSynthesisController
 * @description: 语音合成控制类
 * @Date 2022/5/6 15:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("api")
public class VoiceSynthesisController {
    @Autowired
    private ReadService readService;
    /**
     * 语音合成
     * @return
     */
    //TODO 获取字符串的方式尚未确定，一次最多录入300个字符串
    @PostMapping("readNews")
    public Msg<List<String>> readNews(String nid){
        Msg<List<String>> msg = readService.ReadNews(nid);
        return msg;
    }




}
