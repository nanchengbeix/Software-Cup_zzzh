package com.ycu.zzzh.visual_impairment_3zh.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName FaceResult
 * @description:
 * @Date 2022/5/2 17:14
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaceResult {
    /**
     * 人脸库id
     */
    private String id;
    /**
     * 人脸库名称
     */
    private String name;
    /**
     * 人脸库描述
     */
    private String description;
    /**
     * 自定义字段
     */
    private List<String> exDescriptions;
    /**
     * 库内人员数目
     */
    private Integer memberCount;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private  long updateTime;
}
