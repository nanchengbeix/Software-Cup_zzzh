package com.ycu.zzzh.visual_impairment_3zh.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.common.constant.CommonConstants;
import com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;

/**
 * @ClassName VoiceSynthesisUtils
 * @description: 语音合成工具类
 * @Date 2022/5/6 16:07
 * @Version 1.0
 **/
public class VoiceSynthesisUtils {
    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        String timeShort = DateUtil.getTimeShort();
        String fileName=timeShort+"tts.raw";
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code.replace("\\n", "\r\n"));
        FileOutputStream out = new FileOutputStream(targetPath + "\\"+fileName);
        out.write(buffer);
        out.close();
    }

    public static String send(VoiceConfig.TtsReqParam vo, String iatHttpUrl) throws Exception {
        HttpPost httpPost = new HttpPost(iatHttpUrl);
        Msg msg=new Msg();
        // 设置HTTP数据
        httpPost.setEntity(new StringEntity(JSON.toJSONString(vo), ContentType.APPLICATION_JSON));
        // httpPost.setHeader("user_id", "user_id0");

        //发送HTTP请求
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String response = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(response);
        if(jsonObject != null) {
            JSONObject body = jsonObject.getJSONObject("body");
            if(body != null) {
                String data = body.getString("data");
                if(data != null && data.length() > 0) {
                    // System.out.println(data);
//                    //TODO 第二个参数改为raw文件(文字转语音的语音文件)存储路径
//                    decoderBase64File(data, "\\home\\myblog\\yin");
                    return data;

                } else {
                    msg.setMsg("no response.");
                    return CommonConstants.ERROE;
                }
            } else {
                System.out.println("no response.body");
                msg.setMsg("no response.body");
                return CommonConstants.ERROE;
            }
        } else {
            System.out.println("no response.jsonObject");
            msg.setMsg("no response.jsonObject");
            return CommonConstants.ERROE;
        }
    }
}
