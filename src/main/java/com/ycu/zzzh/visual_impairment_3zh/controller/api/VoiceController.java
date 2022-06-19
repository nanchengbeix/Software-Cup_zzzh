package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.ycu.zzzh.visual_impairment_3zh.utils.ApiUrlTest;
import com.ycu.zzzh.visual_impairment_3zh.utils.AudioUtils;
import com.ycu.zzzh.visual_impairment_3zh.utils.VoiceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig.accessKey;
import static com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig.secret;

/**
 * @ClassName VoiceController
 * @description: 语音听写控制类
 * @Date 2022/5/4 15:59
 * @Version 1.0
 **/
@RestController
@RequestMapping("api")
public class VoiceController {
    private static final String url =  "/api/lingxiyun/cloud/iat/send_request/v1";
    private static String gatewayAddress = "https://api-wuxi-1.cmecloud.cn:8443";
    private static String iatHttpUrl;
    private static String sid;
//    private static final Logger log = LoggerFactory.getLogger(VoiceController.class);
    /**
     * 语音听写
     * @param voiceFile 音频文件。wav存储位置
     * @return
     * @throws Exception
     */
    @PostMapping("dictation")
    public Map<String, Object> dictation(MultipartFile voiceFile)  {
        //TODO 将示例中的"C:\\工作任务\\AIUI\\移动云\\实时转写demo(1)\\jssecacerts”替换为本地https证书文件全路径，证书文件通过运行 InstallCert 得到
        System.setProperty("javax.net.ssl.trustStore", "D:\\java\\jdk-8\\jre\\lib\\security\\jssecacerts");
        sid = UUID.randomUUID().toString();
        ApiUrlTest urlTest = new ApiUrlTest();
        String urlpath = urlTest.doSignature(url, "POST", accessKey, secret);
          iatHttpUrl = gatewayAddress + urlpath;
        Map<String, String> sessionParam = new HashMap<>();
        sessionParam.put("sid", sid);
        sessionParam.put("aue", "raw");
        sessionParam.put("rate", "16000");
        sessionParam.put("rst", "plain");
        sessionParam.put("rse", "utf8");
        sessionParam.put("bos", "20000");
        sessionParam.put("eos", "20000");
        sessionParam.put("dwa", "wpgs");
//        sessionParam.put("language", "ch");
//        sessionParam.put("hotword", "住手");
        Map<String, Object> map=new HashMap<>();

        //TODO 替换为本地待语音听写的PCM音频文件全路径
//        String file = AudioUtils.convertMP32Pcm(wavFile,);
//        String file="D:\\desktop\\我的文件夹\\软件杯\\移动云\\语音听写demo\\demo2\\tts.raw";
//        AudioUtils.audioPcmToWave(file,"D:\\Common software\\PotPlayer\\Capture\\1.wav");
        URI fileURI = null;
        try {
            fileURI = VoiceUtils.sendFile(voiceFile, sessionParam, 40960,iatHttpUrl,sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            map= VoiceUtils.getResult(iatHttpUrl,sid);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","404");
            return map;
        }finally {
            File del = new File(fileURI);
            //删除临时文件
             boolean result = false;
            int tryCount = 0;
            while(!result && tryCount++ <10)
            {
                System.gc();
                result = del.delete();
            }
        }
    }

}
