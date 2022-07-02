package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

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

    @ExceptionHandler({MethodArgumentNotValidException.class,MissingServletRequestParameterException.class,
                        ConstraintViolationException.class,BindException.class})
    public Msg<?> userException(Throwable e) {
        Msg<?> result = new Msg<>();
        result.setErrCode(400);
        if (e instanceof MissingServletRequestParameterException) {
            String m = MessageFormat.format("缺少参数{0}", ((MissingServletRequestParameterException) e).getParameterName());
            String[] msg = m.split(";");
            result.setMsg(msg[0]);
        }
        if (e instanceof ConstraintViolationException) {
            // 单个参数校验异常
            Set<ConstraintViolation<?>> sets = ((ConstraintViolationException) e).getConstraintViolations();
            if (CollectionUtils.isNotEmpty(sets)) {
                StringBuilder sb = new StringBuilder();
                sets.forEach(error -> {
                    if (error instanceof FieldError) {
                        sb.append(((FieldError) error).getField()).append(":");
                    }
                    sb.append(error.getMessage()).append(";");
                });
                String m = sb.toString();
                String[] msg = m.split(";");
                result.setMsg(msg[0]);
            }
        }
        if (e instanceof MethodArgumentNotValidException) {
            // post请求的对象参数校验异常
            List<ObjectError> errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            String m = getValidExceptionMsg(errors);
            if (StringUtils.isNotBlank(m)) {
                String[] msg = m.split(";");
                result.setMsg(msg[0]);
            }
        }
        if (e instanceof BindException) {
            // get请求的对象参数校验异常
            List<ObjectError> errors = ((BindException) e).getBindingResult().getAllErrors();
            String m = getValidExceptionMsg(errors);
            if (StringUtils.isNotBlank(m)) {
                String[] msg = m.split(";");
                result.setMsg(msg[0]);
            }
        }
        return result;
    }
        private String getValidExceptionMsg(List<ObjectError> errors) {
            if(CollectionUtils.isNotEmpty(errors)){
                StringBuilder sb = new StringBuilder();
                errors.forEach(error -> {
                    if (error instanceof FieldError) {
                        sb.append(((FieldError)error).getField()).append(":");
                    }
                    sb.append(error.getDefaultMessage()).append(";");
                });
                String msg = sb.toString();
                return msg;
            }
            return null;
        }


        // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public Object globalException(Throwable ex) {
        Msg<Object> ret = new Msg<Object>();
        ret.setErrCode(500);
        ret.setMsg(ex.getMessage());
        return ret;
    }

}