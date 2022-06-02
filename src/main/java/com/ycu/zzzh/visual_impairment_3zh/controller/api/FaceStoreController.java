package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.cmss.sdk.face.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.face.http.constant.Region;
import com.chinamobile.cmss.sdk.face.http.signature.Credential;
import com.chinamobile.cmss.sdk.face.request.IECloudRequest;
import com.chinamobile.cmss.sdk.face.request.face.FaceRequestFactory;
import com.ycu.zzzh.visual_impairment_3zh.utils.ImageBase64Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FaceStoreController
 * @description: 对人脸库进行操作
 * @Date 2022/5/3 19:13
 * @Version 1.0
 **/
@RestController
@RequestMapping("api")
public class FaceStoreController {
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
     * 创建人脸库
     * @param name
     * @return
     */
    @RequestMapping("createFaceStore")
    public JSONObject createFaceStore(String name){
        JSONObject params = new JSONObject();
        List<String> list=new ArrayList<>();
        list.add("FACE_TOKEN");
        list.add("username");

        params.put("description", "人脸库实例");//人脸库id:6273cc5cfd04d200010064bb
        params.put("name", name);
        params.put("exDescriptions", list);
        JSONObject response = null;
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/create", params);
        try{
            response =(JSONObject)client.call(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 删除人脸库
     * @param faceStoreId
     * @return
     */
    public JSONObject deleteFaceStore(String faceStoreId){
        JSONObject params = new JSONObject();
        params.put("faceStoreId", faceStoreId);
        JSONObject response = null;
        IECloudRequest faceStoreListRequest = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/delete", params);
        try{
            response = (JSONObject) client.call(faceStoreListRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 更新人脸库
     * @param faceStoreId
     * @param name
     * @param description
     * @return
     */
    @RequestMapping("updateFaceStore")
    public JSONObject updateFaceStore(String faceStoreId,String name,String description){
        JSONObject params = new JSONObject();
        params.put("description", description);
        params.put("name", name);
        params.put("faceStoreId", faceStoreId);
        JSONObject response = null;
        IECloudRequest request = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/update", params);
        try{
            response =(JSONObject)client.call(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 查询人脸库列表
     * @return
     */
    @RequestMapping("getFaceStoreList")
    public JSONObject getFaceStoreList(){
        JSONObject params = new JSONObject();
        params.put("offset", 1);
        params.put("limit",10);
        JSONObject response = null;
        IECloudRequest faceStoreListRequest = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/list", params);
        try{
             response = (JSONObject) client.call(faceStoreListRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 查询人脸库
     * @param faceStoreId
     * @return
     */
    @RequestMapping("getFaceStore")
    public JSONObject getFaceStore(String faceStoreId){
        JSONObject params = new JSONObject();
        params.put("faceStoreId", faceStoreId);
        JSONObject response = null;
        IECloudRequest faceStoreListRequest = FaceRequestFactory.getFaceRequest("/api/human/face/v1/store/get", params);
        try{
            response = (JSONObject) client.call(faceStoreListRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

}
