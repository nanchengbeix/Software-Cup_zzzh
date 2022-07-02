package com.ycu.zzzh.visual_impairment_3zh.service.impl;

import com.ycu.zzzh.visual_impairment_3zh.common.constant.ApiConstants;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.ApiUrlTest;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.CharacterUtil;
import com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.NewsService;
import com.ycu.zzzh.visual_impairment_3zh.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ycu.zzzh.visual_impairment_3zh.common.utils.VoiceSynthesisUtils.send;

/**
 * @ClassName ReadServiceImpl
 * @description: TODO
 * @Date 2022/6/30 20:07
 * @Version 1.0
 **/
@Service
public class ReadServiceImpl implements ReadService {
    private static String url = "/api/lingxiyun/cloud/tts/v1";
    private static String gatewayAddress = "https://api-wuxi-1.cmecloud.cn:8443";
    public static String iatHttpUrl;

    @Autowired
    private NewsService newsService;




    public String ReadText(String text) {
            //TODO 替换为本地https证书文件全路径，证书文件通过运行 InstallCert 得到
            System.setProperty("javax.net.ssl.trustStore", "D:\\java\\jdk-8\\jre\\lib\\security\\jssecacerts");
            ApiUrlTest urlTest = new ApiUrlTest();
            String msg = null;
            String urlpath = urlTest.doSignature(url, "POST", ApiConstants.USER_AK, ApiConstants.USER_SK);
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

    @Override
    public Msg<List<String>> ReadNews(String nid) {
        Msg<List<String>> msg = new Msg<>();
        Map<String, Object> map = newsService.newsContentInfoService(nid);
        String news= String.valueOf(map.get("newsContent"));
        List<String> strings = CharacterUtil.stringToStringArray(news, 295);
        List<String> base64List=new ArrayList<>();
        for (String s : strings){
            String data = ReadText(s);
            base64List.add(data);
        }
        msg.setData(base64List);
        return msg;
    }
}
