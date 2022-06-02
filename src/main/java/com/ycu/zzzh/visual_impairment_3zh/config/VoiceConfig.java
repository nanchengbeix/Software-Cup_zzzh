package com.ycu.zzzh.visual_impairment_3zh.config;

import java.util.Map;

/**
 * @ClassName VoiceConfig
 * @description: TODO
 * @Date 2022/5/22 10:01
 * @Version 1.0
 **/
public class VoiceConfig {
    //TODO 使用移动云账户登录 https://ecloud.10086.cn/api/page/op-usercenter-static/#/aksk?productType=accesskey
    //得到用户的 accessKey 和 secret，然后替换下面对应变量
    public static  String accessKey = "3e94606c57e94776a6733ef43700bb91";
    public static String secret =  "0605fd2960dd44d49f4c3afb5c733c13";


    public static class TtsReqParam {
        public Map<String, String> sessionParam;
        public String text;
        // public String format;
    }

    public static class IatReqVo {
        public Map<String, String> sessionParam;
        public String data;
        public Integer endFlag;
    }
}
