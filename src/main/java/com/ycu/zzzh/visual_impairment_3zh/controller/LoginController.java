package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinamobile.cmss.sdk.face.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.face.http.constant.Region;
import com.chinamobile.cmss.sdk.face.http.signature.Credential;
import com.chinamobile.cmss.sdk.face.request.IECloudRequest;
import com.chinamobile.cmss.sdk.face.request.face.FaceRequestFactory;
import com.ycu.zzzh.visual_impairment_3zh.common.constant.ApiConstants;
import com.ycu.zzzh.visual_impairment_3zh.common.constant.UrlConstants;
import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.UserFace;
import com.ycu.zzzh.visual_impairment_3zh.model.request.createFaceRequest;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserFaceService;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登陆与注册操作
 * @author 胡富国
 */
@RestController
@Validated
public class LoginController {

    private static ECloudDefaultClient client;

    private final UserService userService;
    private final LogService logService;
    private final UserFaceService userFaceService;

    static {

        Credential credential = new Credential(ApiConstants.USER_AK, ApiConstants.USER_SK);
        client = new ECloudDefaultClient(credential, Region.POOL_SZ);
    }
    public LoginController(UserService userService, LogService logService,UserFaceService userFaceService) {
        this.userService = userService;
        this.logService = logService;
        this.userFaceService=userFaceService;
    }

    /**
     * 用户登录
     *
     */
    @PostMapping(value = "/login")
    public Object userLogin(@RequestParam(name = "username") String userName,
                            @RequestParam(name = "password") String password,
                            ServletResponse response) {

        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg ;
        boolean loginSuccess = false;
        // 将用户名和密码封装成 UsernamePasswordToken 对象
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try {
            subject.login(token);
            msg = "登录成功";
            loginSuccess = true;
        } catch (UnknownAccountException uae) { // 账号不存在
            msg = "用户不存在，请注册";
        } catch (LockedAccountException lae) { // 账号已被锁定
            msg = "该用户被封号,暂时无法登录！";
        } catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
            msg = "用户名与密码不匹配，请检查后重新输入！";
        }  catch (AuthenticationException ae) { // 其他身份验证异常
            msg = "登录异常，请联系管理员！";
        }
        Msg<Object> ret = new Msg<>();
        if (loginSuccess) {
            User user=userService.findByUserName(userName);
            // 若登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(userName,String.valueOf(user.getUid()),JwtUtils.SECRET);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            ret.setMsg(msg);
        } else {
            ret.setErrCode(401);
            ret.setMsg(msg);
            String message="%s"+msg;
            logService.logError(message,userName);
        }
        return ret;
    }

    /**
     * 人脸登录
     * @param faceRequest 存储图片的Base64
     */
    @PostMapping("humanLogin")
    public Msg<?> humanLogin(@RequestBody createFaceRequest faceRequest,ServletResponse httpResponse){
        List<String> list=new ArrayList<>();
        list.add(ApiConstants.FACESTOREID);
        JSONObject params = new JSONObject();
        params.put("image",faceRequest.getImage());
        params.put("imageType","BASE64");
        params.put("faceStoreIds",list);
        params.put("maxFaceNum",3);
        IECloudRequest<?> request = FaceRequestFactory.getFaceRequest(UrlConstants.SEARCHFACE, params);
        JSONObject response = null;
        Msg<?> msg=new Msg<>();
        try{
            response =(JSONObject)client.call(request);

        }catch (Exception e){
            e.printStackTrace();
        }
        double minTrust = response.getJSONObject("body").getJSONArray("results").getJSONObject(0).getDouble("confidence");
        if (minTrust>=ApiConstants.MINTRUST){
            msg.setMsg("人脸登录成功");
            String uid = response.getJSONObject("body").getJSONArray("results").getJSONObject(0).getString("memberName");
            User user = userService.getById(uid);
            // 若登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(user.getUsername(),String.valueOf(user.getUid()),JwtUtils.SECRET);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) httpResponse).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
        }else {
            msg.setMsg("人脸登录失败");
            msg.setErrCode(412);
        }
        return msg;

    }

    /**
     * 用户注册
     * @param user 用户填写的个人信息（昵称，密码，手机号，喜欢分类，出生日期，所在地）
     */
    @PostMapping("/registered")
    public Msg<?> registered(@Valid @RequestBody  User user){
        Msg<?> ret = new Msg<>();
        String zh= String.valueOf(Math.random()).substring(2,10);
        user.setUsername(zh);
        userService.addUserInfoService(user);
        ret.setMsg("注册成功");
        return ret;
    }

    /**
     * 用户人脸注册
     *
     */
    @PostMapping("faceRegister")
    public Msg<?> faceRegister(@RequestBody createFaceRequest createfaceRequest, ServletRequest req) {
        //TODO 需要判断人脸是否已经存在，是：删除人脸库中的数据，否：直接添加即可
        Msg<?> msg=new Msg<>();
        //获取请求头中的token中的uid
        String uid = JwtUtils.tokenToId(req);
        QueryWrapper<UserFace> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        UserFace one = userFaceService.getOne(wrapper);
        if (one!=null){
            msg.setErrCode(401);
            msg.setMsg("用户已有人脸信息，请先删除人脸信息再重新录入");
            return msg;
        }
        JSONObject params = new JSONObject();
        //人脸库id写死
        params.put("faceStoreId", ApiConstants.FACESTOREID);
        params.put("imageType", "BASE64");
        params.put("name",uid);
        params.put("image", createfaceRequest.getImage());
        IECloudRequest<?> request = FaceRequestFactory.getFaceRequest(UrlConstants.CREATFACE, params);
        JSONObject response = null;
        try {
            response = (JSONObject) client.call(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将人脸库返回数据存储到数据库中
        return (Msg<?>) userFaceService.addUserFaceService(response);
    }
}
