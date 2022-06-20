package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.utils.ApiUrlTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig.secret;
import static com.ycu.zzzh.visual_impairment_3zh.utils.VoiceSynthesisUtils.send;

/**
 * @ClassName VoiceSynthesisController
 * @description: 语音合成控制类
 * @Date 2022/5/6 15:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("api")
public class VoiceSynthesisController {
    private static String url = "/api/lingxiyun/cloud/tts/v1";
    private static String gatewayAddress = "https://api-wuxi-1.cmecloud.cn:8443";
    public static String iatHttpUrl;
    /**
     * 语音合成
     * @return
     */
    //TODO 获取字符串的方式尚未确定，一次最多录入300个字符串
    @PostMapping("readText")
    public Msg readText(String text){
        //TODO 替换为本地https证书文件全路径，证书文件通过运行 InstallCert 得到
        System.setProperty("javax.net.ssl.trustStore", "D:\\java\\jdk-8\\jre\\lib\\security\\jssecacerts");
        ApiUrlTest urlTest = new ApiUrlTest();
        Msg msg=new Msg();
        String urlpath = urlTest.doSignature(url, "POST", VoiceConfig.accessKey, secret);
        iatHttpUrl = gatewayAddress + urlpath;
        Map<String, String> sessionParam = new HashMap<>();
        sessionParam.put("sid", UUID.randomUUID().toString());
        //TODO 替换为期望的发音人，中文发音人列表：xiaoyan;xiaofeng;xiaoai_Novel;dahuilang;yuanye;yiping;xiaoxi
        // 英文发音人列表：pe_Catherine;pe_John
        sessionParam.put("native_voice_name", "xiaoyan");
        sessionParam.put("audio_coding", "raw");
        sessionParam.put("pitch", "100");
        sessionParam.put("speed", "100");
        sessionParam.put("read_all_marks","0");
        sessionParam.put("read_number","0");
        sessionParam.put("volume", "10");
        // sessionParam.put("frame_size", "0");
        VoiceConfig.TtsReqParam vo = new VoiceConfig.TtsReqParam();
        vo.sessionParam = sessionParam;
        //TODO 替换为待语音合成的输入文本
        vo.text = text;
        try {
            msg=send(vo,iatHttpUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }



}
