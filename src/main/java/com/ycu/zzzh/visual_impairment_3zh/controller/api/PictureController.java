package com.ycu.zzzh.visual_impairment_3zh.controller.api;

import com.chinamobile.cmss.sdk.image.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.image.ECloudServerException;
import com.chinamobile.cmss.sdk.image.IECloudClient;
import com.chinamobile.cmss.sdk.image.http.constant.Region;
import com.chinamobile.cmss.sdk.image.http.signature.Credential;
import com.chinamobile.cmss.sdk.image.request.EngineImageClassifyDetectPostRequest;
import com.chinamobile.cmss.sdk.image.response.EngineImageClassifyDetectResponse;
import com.chinamobile.cmss.sdk.image.response.bean.EngineClassify;
import com.chinamobile.cmss.sdk.image.util.JacksonUtil;
import com.ycu.zzzh.visual_impairment_3zh.config.VoiceConfig;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogsString;
import com.ycu.zzzh.visual_impairment_3zh.model.request.BaseReceive;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName PictureController
 * @description: 对图片内容进行识别
 * @Date 2022/6/12 20:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("api")
public class PictureController {

    private final LogService logService;

    public PictureController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 图像识别
     * @param base64
     * @return
     */
    @PostMapping(value = {"imageRecognition"})
    public Msg<String[]> imageRecognition(@RequestBody BaseReceive base64){
        Msg<String[]> msg=new Msg<>();
        Credential credential = new Credential(VoiceConfig.accessKey, VoiceConfig.secret);

        //初始化ECloud client,Region 为部署资源池OP网关地址枚举类，可自行扩展
        IECloudClient ecloudClient = new ECloudDefaultClient(credential, Region.POOL_SZ);

        //待定义产品request
        try {
        //通用图像识别
        EngineImageClassifyDetectPostRequest request = new EngineImageClassifyDetectPostRequest();
        //行人属性
        //EngineImagePersonDetectPostRequest request = new EngineImagePersonDetectPostRequest();

        //图片base64 ，注意不要包含 {data:image/jpeg;base64,}
        request.setImage(base64.getBase64());

        //通用图像识别
        EngineImageClassifyDetectResponse response = ecloudClient.call(request);
        //人体检测与属性识别
        //EngineImagePersonDetectResponse response = ecloudClient.call(request);

        if("OK".equals(response.getState())){
            //通用图像识别
            List<EngineClassify> body = response.getBody();
            //人体检测与属性识别
            //List<EnginePerson> body = response.getBody();
            EngineClassify engineClassify = body.get(0);
            String classes = engineClassify.getClasses();
            String[] datas = classes.split(",");
            String[] data=new String[4];
            for (int i=0;i<4;i++){
                data[i]=datas[i];
            }
            msg.setMsg("success");
            msg.setData(data);
        }
        } catch (IOException|ECloudServerException | IllegalAccessException  e) {
            logService.logError(LogsString.serverError,PictureController.class.getSimpleName(),e.getMessage());
        }
        return msg;
    }

}
