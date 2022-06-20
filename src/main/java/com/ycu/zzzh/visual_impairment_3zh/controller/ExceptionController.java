package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理全局异常
 */
@RestControllerAdvice
public class ExceptionController {

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    public Object handleShiroException(ShiroException e) {
        Msg<Object> ret = new Msg<Object>();
        ret.setErrCode(401);
        ret.setMsg(e.getMessage());
        return ret;
    }

    //捕获无权限异常
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Object handleShiroException(Exception ex) {
        Msg<Object> ret = new Msg<Object>();
        ret.setErrCode(403);
        ret.setMsg("无权限");
        return ret;
    }
    //捕获鉴权失败异常
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Object AuthorizationException(Exception ex) {
        Msg<Object> ret = new Msg<Object>();
        ret.setErrCode(403);
        ret.setMsg("权限认证失败");
        return ret;
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, Throwable ex) {
        Msg<Object> ret = new Msg<Object>();
        ret.setErrCode(500);
        ret.setMsg(ex.getMessage());
        return ret;
    }

}