package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.cmss.sdk.face.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.face.http.constant.Region;
import com.chinamobile.cmss.sdk.face.http.signature.Credential;
import com.chinamobile.cmss.sdk.face.request.IECloudRequest;
import com.chinamobile.cmss.sdk.face.request.face.FaceRequestFactory;
import com.ycu.zzzh.visual_impairment_3zh.model.request.createFaceRequest;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    static {

        user_ak = "3e94606c57e94776a6733ef43700bb91";
        user_sk = "0605fd2960dd44d49f4c3afb5c733c13";

        Credential credential = new Credential(user_ak, user_sk);
        client = new ECloudDefaultClient(credential, Region.POOL_SZ);
    }

    /**
     * 将用户人脸录入人脸库
     * @param createfaceRequest 图片Base64 用户id
     * @return
     */
    @PostMapping("createFace")
    public JSONObject createFace(@RequestBody createFaceRequest createfaceRequest) {
        JSONObject params = new JSONObject();

        Msg msg = new Msg();
        //TODO 人脸库id写死
        params.put("faceStoreId", "6273cc5cfd04d200010064bb");
        params.put("imageType", "BASE64");
        params.put("name",createfaceRequest.getUid());
        params.put("image", createfaceRequest.getImage());
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/create", params);
        JSONObject response = null;
        try {
            response = (JSONObject) client.call(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 需要取出人脸库人员ID与数据库结合
        return response;
    }

    /**
     * 删除用户人脸库信息
     * @param memberId
     * @return
     */
    @PostMapping("deleteFace")
    public  JSONObject deleteFace(String memberId){
        JSONObject params = new JSONObject();
        params.put("memberId",memberId);
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/delete", params);
        JSONObject response = null;
        try{
            response =(JSONObject)client.call(request);

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("getFace")
    public JSONObject getFace(String memberId){
        JSONObject params = new JSONObject();
        params.put("memberId",memberId);
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/member/get", params);
        JSONObject response = null;
        try{
            response =(JSONObject)client.call(request);

        }catch (Exception e){
            e.printStackTrace();
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
