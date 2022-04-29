package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ToResult
 * @description: TODO
 * @Date 2022/4/9 19:55
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToResult {
    private boolean success;
    private String message;
}
