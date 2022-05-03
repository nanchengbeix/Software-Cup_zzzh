package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName faceController
 * @description: TODO 人脸识别
 * @Date 2022/5/1 21:05
 * @Version 1.0
 **/
@RequestMapping("face")
@RestController
public class faceController {

    public Map<String,Object> createFaceIn(String name, String description, List<String> exDescriptions){

        return null;
    }
}
