package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageResult
 * @description: 用来存储分页返回参数
 * @Date 2022/4/9 20:23
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private String msg;
    private Integer currentPage;
    private Long pageSize;
    private List<T> data;

}
