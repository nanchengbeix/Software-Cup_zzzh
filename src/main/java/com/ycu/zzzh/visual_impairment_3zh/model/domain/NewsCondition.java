package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName NewsCondition
 * @description: TODO 新闻检索的条件
 * @Date 2022/4/21 19:42
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCondition {
    private String title;
    private String tag;
    private String dateorder;
}
