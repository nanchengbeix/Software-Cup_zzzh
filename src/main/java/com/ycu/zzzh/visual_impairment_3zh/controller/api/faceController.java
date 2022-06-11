package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinamobile.cmss.sdk.face.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.face.http.constant.Region;
import com.chinamobile.cmss.sdk.face.http.signature.Credential;
import com.chinamobile.cmss.sdk.face.request.IECloudRequest;
import com.chinamobile.cmss.sdk.face.request.face.FaceRequestFactory;
import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserFace;
import com.ycu.zzzh.visual_impairment_3zh.model.request.createFaceRequest;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserFaceService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.*;

/**
 * @ClassName faceController
 * @description:  人脸识别
 * @Date 2022/5/1 21:05
 * @Version 1.0
 **/
@RequestMapping("api")
@RestController
public class faceController {
    public static String user_ak;
    private static String user_sk;
    private static ECloudDefaultClient client;

    private final UserFaceService userFaceService;
    private final LogService logService;

    static {

        user_ak = "3e94606c57e94776a6733ef43700bb91";
        user_sk = "0605fd2960dd44d49f4c3afb5c733c13";

        Credential credential = new Credential(user_ak, user_sk);
        client = new ECloudDefaultClient(credential, Region.POOL_SZ);
    }

    public faceController(UserFaceService userFaceService,LogService logService) {
        this.userFaceService = userFaceService;
        this.logService = logService;
    }

    /**
     * 将用户人脸录入人脸库
     * @param createfaceRequest 图片Base64 用户id
     * @return
     */
    @PostMapping("createFace")
    public Msg createFace(@RequestBody createFaceRequest createfaceRequest,ServletRequest req) {
        //TODO 需要判断人脸是否已经存在，是：删除人脸库中的数据，否：直接添加即可
        Msg msg=new Msg();
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(req);
        JSONObject params = new JSONObject();
        //TODO 人脸库id写死
        params.put("faceStoreId", "6273cc5cfd04d200010064bb");
        params.put("imageType", "BASE64");
        params.put("name",uid);
        params.put("image", createfaceRequest.getImage());
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/create", params);
        JSONObject response = null;
        try {
            response = (JSONObject) client.call(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将人脸库返回数据存储到数据库中
        Msg result = userFaceService.addUserFaceService(response);
        return result;
    }

    /**
     * 删除用户人脸库信息
     * @param req
     * @return
     */
    @PostMapping("deleteFace")
    public  Msg deleteFace(ServletRequest req){
        Msg msg=new Msg();
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(req);
        if (StringUtils.hasText(uid)){
            QueryWrapper<UserFace> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("uid",uid);
            UserFace one = userFaceService.getOne(queryWrapper);
            if (one != null){
                String memberId = one.getId();
                JSONObject params = new JSONObject();
                params.put("memberId",memberId);
                IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/delete", params);
                JSONObject response = null;
                try{
                    response =(JSONObject)client.call(request);

                }catch (Exception e){
                    logService.logError(e.getMessage());
                }
                userFaceService.removeById(memberId);
                msg.setMsg(response.getString("state"));
                return msg;
            }else {
                msg.setMsg("用户无人脸信息");
                return msg;
            }

        }else {
            msg.setErrCode(403);
            msg.setMsg("禁止访问");
            return msg;
        }

    }

    /**
     * 查询人脸信息
     * @param memberId
     * @return
     */
    @RequestMapping("getFace")
    public JSONObject getFace(String memberId){
        JSONObject params = new JSONObject();
        params.put("memberId",memberId);
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/get", params);
        JSONObject response = null;
        try{
            response =(JSONObject)client.call(request);

        }catch (Exception e){
            logService.logError(e.getMessage());
        }
        return response;
    }

    /**
     * 查询人脸库人员列表
     * @param faceStoreId
     * @return
     */
    @RequestMapping("getFaceList")
    public JSONObject getFaceList(String faceStoreId){
        JSONObject params = new JSONObject();
        params.put("faceStoreId",faceStoreId);
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/list", params);
        JSONObject response = null;
        try{
            response =(JSONObject)client.call(request);
        }catch (Exception e){
            logService.logError(e.getMessage());
        }
        return response;
    }



    /**
     * 人脸搜索
     * @param faceRequest
     * @return
     */
    @PostMapping("searchHuman")
    public Map<String,Object>  searchHuman(@RequestBody createFaceRequest faceRequest){
        List<String> list=new ArrayList<>();
        list.add("6273cc5cfd04d200010064bb");
        JSONObject params = new JSONObject();
        params.put("image",faceRequest.getImage());
        params.put("imageType","BASE64");
        params.put("faceStoreIds",list);
        params.put("maxFaceNum",3);
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/search", params);
        JSONObject response = null;
        Msg msg=new Msg();
        Map<String,Object> map=new HashMap<>();
        try{
            response =(JSONObject)client.call(request);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(response.getJSONObject("body").getJSONArray("results").getJSONObject(0).getDouble("confidence"));
        if (response.getJSONObject("body").getJSONArray("results").getJSONObject(0).getDouble("confidence")>=0.73){
            msg.setMsg("识别成功");
        }else {
            msg.setMsg("识别失败");
            msg.setErrCode(412);
        }
        map.put("result",msg);
        map.put("data",response);
        return map;




    }


}
