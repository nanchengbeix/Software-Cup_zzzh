package com.ycu.zzzh.visual_impairment_3zh.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName SortResult
 * @description: 存储新闻分类查询返回参数
 * @Date 2022/4/27 16:17
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortResult<T> {
    private String msg;
    private Integer totalTagNum;
    private List<T> tag;
}
