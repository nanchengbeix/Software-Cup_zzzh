package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.cmss.sdk.ocr.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.ocr.http.constant.Region;
import com.chinamobile.cmss.sdk.ocr.http.signature.Credential;
import com.chinamobile.cmss.sdk.ocr.request.IECloudRequest;
import com.chinamobile.cmss.sdk.ocr.request.ocr.OcrRequestFactory;
import com.ycu.zzzh.visual_impairment_3zh.model.request.BaseReceive;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName OCRController
 * @description: TODO
 * @Date 2022/5/3 19:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("api")
public class OCRController {

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
     * 通用印刷体识别
     * @param baseReceive
     * @return
     */
    @PostMapping("general")
    public JSONObject general(@RequestBody BaseReceive baseReceive) {
        HashMap<String, Object> generalParams = new HashMap<>();
        JSONObject generalOptions = new JSONObject();
        generalOptions.put("language", "zh");
        generalOptions.put("preprocess_max_angle", 90);
        generalParams.put("options", generalOptions);

        //参数为图片的base64编码
        IECloudRequest generalRequestBase64 = OcrRequestFactory.getOcrBase64Request("/api/ocr/v1/general", baseReceive.getBase64(), generalParams);

        //TODO 假设不会出现异常，后期再优化
        JSONObject responseBase64 = null;
        try {
            responseBase64 = (JSONObject) client.call(generalRequestBase64);
            return responseBase64;
        } catch (IOException e) {
            e.printStackTrace();
            return responseBase64;
        }
    }
        @PostMapping("handwriting")
        public JSONObject handwriting(@RequestBody BaseReceive baseReceive){
            System.out.println(baseReceive.getBase64());
            HashMap<String, Object> generalParams = new HashMap<>();
            JSONObject generalOptions = new JSONObject();
            generalOptions.put("language", "zh");
            generalParams.put("options", generalOptions);

            //参数为图片的base64编码
            IECloudRequest generalRequestBase64 =  OcrRequestFactory.getOcrBase64Request("/api/ocr/v1/handwriting",baseReceive.getBase64(),generalParams);

                //TODO 假设不会出现异常，后期再优化
            JSONObject responseBase64 = null;
            try {
                responseBase64 = (JSONObject) client.call(generalRequestBase64);
                return responseBase64;
            } catch (IOException e) {
                e.printStackTrace();
                return responseBase64;
        }


    }
}
