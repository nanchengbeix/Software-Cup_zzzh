package com.ycu.zzzh.visual_impairment_3zh.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig;
import com.ycu.zzzh.visual_impairment_3zh.controller.api.VoiceSynthesisController;
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
 * @description: TODO
 * @Date 2022/5/6 16:07
 * @Version 1.0
 **/
public class VoiceSynthesisUtils {
    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code.replace("\\n", "\r\n"));
        FileOutputStream out = new FileOutputStream(targetPath + "\\tts1.raw");
        out.write(buffer);
        out.close();
    }

    public static Msg send(VoiceConfig.TtsReqParam vo, String iatHttpUrl) throws Exception {
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
            System.out.println(jsonObject.toJSONString());
            JSONObject body = jsonObject.getJSONObject("body");
            if(body != null) {
                String data = body.getString("data");
                if(data != null && data.length() > 0) {
                    // System.out.println(data);
                    //TODO 第二个参数改为raw文件存储路径
                    decoderBase64File(data, "D:\\desktop\\我的文件夹\\软件杯\\移动云\\api示例文件");
                    System.out.println("tts.raw created.");
                    msg.setMsg("tts.raw created.");
                    return msg;

                } else {
                    System.out.println("no response.");
                    msg.setMsg("no response.");
                    return msg;
                }
            } else {
                System.out.println("no response.body");
                msg.setMsg("no response.");
                return msg;
            }
        } else {
            System.out.println("no response.jsonObject");
            msg.setMsg("no response.");
            return msg;
        }
    }
}
