package com.ycu.zzzh.visual_impairment_3zh.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName FaceRequest
 * @description: TODO
 * @Date 2022/5/5 17:29
 * @Version 1.0
 **/
@AllArgsConstructor
@Data
@NoArgsConstructor
public class FaceRequest {
    private String faceId;
    private String image;
    private String name;
}
