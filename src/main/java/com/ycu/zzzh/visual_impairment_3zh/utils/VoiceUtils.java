package com.ycu.zzzh.visual_impairment_3zh.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig;
import com.ycu.zzzh.visual_impairment_3zh.controller.api.VoiceController;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName VoiceUtils
 * @description: TODO
 * @Date 2022/5/6 9:51
 * @Version 1.0
 **/
public class VoiceUtils {
    private static  String accessKey = "3e94606c57e94776a6733ef43700bb91";
    private static String secret =  "0605fd2960dd44d49f4c3afb5c733c13";
    private static String url =  "/api/lingxiyun/cloud/iat/send_request/v1";
    private static String url2 = "/api/lingxiyun/cloud/iat/query_result/v1";
    private static String gatewayAddress = "https://api-wuxi-1.cmecloud.cn:8443";
    public static String sendFile(MultipartFile multipartFile, Map<String, String> sessionParam, int sliceSize, String iatHttpUrl, String sid) throws Exception {
        File file=null;
        file=FileUtil.multipartFileToFile(multipartFile);
        if (!file.exists()) {
            return "文件不存在:" + file.getAbsolutePath();
        }

        int sliceNum = (int) Math.ceil((double) file.length() / sliceSize);
        for (int i = 0; i < sliceNum; i++) {
            byte[] data = getData(file, i * sliceSize, sliceSize);
            if (data.length == 0) {
                break;
            }
            if (i > 0) {
                sessionParam = null;
            }

            send(String.valueOf(i+1), sessionParam, data, i == sliceNum - 1 ? 1 : 0,iatHttpUrl,sid);
        }
        FileUtil.deleteTempFile(file );
        return "分片发送完成，等待接收转写结果";
    }

    public static void send(String number, Map<String, String> sessionParam, byte[] audioBytes, int endFlag,String iatHttpUrl, String sid) throws Exception {
        HttpPost httpPost = new HttpPost(iatHttpUrl);
        httpPost.setHeader("streamId", sid);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("number", number);
        httpPost.setHeader("language", "cn");
        // httpPost.setHeader("user_id", "user_id0");

        // 设置HTTP数据
        String audioStr = Base64.getEncoder().encodeToString(audioBytes);
        VoiceConfig.IatReqVo vo = new VoiceConfig.IatReqVo();
        vo.data = audioStr;
        vo.endFlag = endFlag;
        vo.sessionParam = sessionParam;

        httpPost.setEntity(new StringEntity(JSON.toJSONString(vo), ContentType.APPLICATION_JSON));


        //发送HTTP请求
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        String response = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(response);
        if(jsonObject != null) {
            if (jsonObject.get("body") == null
                    || jsonObject.getJSONArray("body").size() > 0) {
                System.out.println(response);
            }
        } else {
            System.out.println("语音听写分片发送无响应");
        }
    }
    public static byte[] getData(File file, int from, int size) throws Exception {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] tempbytes = new byte[Math.min(in.available(), size)];
            in = new FileInputStream(file);
            in.skip(from);
            in.read(tempbytes);
            return tempbytes;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    // ignore
                }
            }
        }
    }
    public static Map<String, Object> getResult(String msg,String iatHttpUrl,String sid) throws Exception {
        ApiUrlTest urlTest = new ApiUrlTest();
        String urlpath = urlTest.doSignature(url2, "GET", accessKey, secret);
        iatHttpUrl = gatewayAddress + urlpath;
        HttpGet httpGet = new HttpGet(iatHttpUrl);
        httpGet.setHeader("streamId", sid);
        httpGet.setHeader("Content-Type", "application/json");
        //发送HTTP请求
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String response = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(response);
        if(jsonObject != null) {
            System.out.println("转写结果：" + jsonObject);
        } else {
            System.out.println("语音听写无转写结果");
            msg="error";
        }
        Map<String,Object> map=new HashMap<>();
        map.put("msg",msg);
        map.put("data",jsonObject);
        return map;
    }
}
