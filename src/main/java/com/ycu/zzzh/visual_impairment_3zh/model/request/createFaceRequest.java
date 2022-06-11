package com.ycu.zzzh.visual_impairment_3zh.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName createFaceRequest
 * @description:创建人脸接口请求对象
 * @Date 2022/5/5 21:34
 * @Version 1.0
 **/
@AllArgsConstructor
@Data
@NoArgsConstructor
public class createFaceRequest {
    /**
     * 人脸图片Base64编码
     */
    String image;
    /**
     * 人脸库id
     */
    String faceStoreId;
}
