package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.ycu.zzzh.visual_impairment_3zh.mapper.UserPhotoMapper;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserPhoto;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserAvatarService;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.FileUtil;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.ImageBase64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName userAvatarServiceImpl
 * @description: TODO
 * @Date 2022/5/30 17:45
 * @Version 1.0
 **/
@Service
public class userAvatarServiceImpl implements UserAvatarService {
    @Autowired
    private UserPhotoMapper userPhotoMapper;
    @Override
    public Msg userAvatarUploadService(MultipartFile photo, ServletRequest request,String uid) throws IOException {
        Msg msg=new Msg();
        byte[] bytes = photo.getBytes();
        InputStream inputStream=new ByteArrayInputStream(bytes);
        if (!FileUtil.isImage(inputStream)){
            msg.setErrCode(400);
            msg.setMsg("用户上传的文件非图片格式");
            return msg;
        }
        //
        //1.查询用户是否已有图像
        UserPhoto userPhoto = userPhotoMapper.selectById(uid);
        if (userPhoto!=null){
            //TODO 暂不考虑会出错的问题
                File myDelFile = new File(userPhoto.getFileMkdirs());
                myDelFile.delete();
                userPhotoMapper.deleteById(userPhoto.getUid());
        }

        //1.确定文件存储路径
        //使用ServletContext对象动态获取项目根目录下的upload文件夹的路径，作为资源存储路径
        String path=request.getServletContext().getRealPath("/upload");
        System.out.println(path);
        //2.确定文件存储的名字
        //获取文件的原始名
        String oldName=photo.getOriginalFilename();
        //获取文件存储的后缀名
        String suffix=oldName.substring(oldName.lastIndexOf("."));//.jpg
        //创建文件新的名字 名+后缀 比如 a.jpg
        String newName= UUID.randomUUID()+""+suffix;
        //3.完成存储
        //创建file对象存储资源路径
        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();//创建存储路径
        }
        //输出存储
        photo.transferTo(new File(f,newName));
        UserPhoto setPhoto=new UserPhoto(Integer.valueOf(uid),path+"\\"+newName);
        userPhotoMapper.insert(setPhoto);
        //4.响应结果
        msg.setMsg("上传成功");
        return msg;
    }

    //读取头像返回Base64
    @Override
    public Msg<String> photoToBase64(String uid) {
        Msg<String> msg=new Msg<>();
        UserPhoto userPhoto = userPhotoMapper.selectById(uid);
        if (userPhoto!=null){
            String imageBase64 = ImageBase64Util.getImageString(userPhoto.getFileMkdirs());
            msg.setData(imageBase64);
            msg.setMsg("请求成功");
            return msg;
        }else {
            msg.setMsg("no");
            return msg;
        }
    }
}
