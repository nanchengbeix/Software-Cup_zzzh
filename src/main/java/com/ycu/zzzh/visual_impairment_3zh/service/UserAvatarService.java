package com.ycu.zzzh.visual_impairment_3zh.service;

import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.io.IOException;

public interface UserAvatarService {
    //处理头像上传请求
    Msg userAvatarUploadService(MultipartFile photo, ServletRequest requestm,String uid) throws IOException;
    //处理获取头像请求
    Msg<String> photoToBase64(String uid);
}
