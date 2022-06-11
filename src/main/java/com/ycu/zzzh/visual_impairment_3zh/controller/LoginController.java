package com.ycu.zzzh.visual_impairment_3zh.controller;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.ycu.zzzh.visual_impairment_3zh.jwt.JwtUtils;
import com.ycu.zzzh.visual_impairment_3zh.logs.LogService;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.ycu.zzzh.visual_impairment_3zh.model.result.Msg;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    private final UserService userService;
    private final LogService logService;

    public LoginController(UserService userService, LogService logService) {
        this.userService = userService;
        this.logService = logService;
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param response
     * @return
     */
    @PostMapping(value = "/login")
    public Object userLogin(@RequestParam(name = "username", required = true) String userName,
                            @RequestParam(name = "password", required = true) String password,
                            ServletResponse response) {

        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        // 将用户名和密码封装成 UsernamePasswordToken 对象
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try {
            subject.login(token);
            msg = "登录成功";
            loginSuccess = true;
        } catch (UnknownAccountException uae) { // 账号不存在
            msg = "用户名与密码不匹配，请检查后重新输入！";
        } catch (LockedAccountException lae) { // 账号已被锁定
            msg = "该用户被封号,暂时无法登录！";
        } catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
            msg = "用户名与密码不匹配，请检查后重新输入！";
        }  catch (AuthenticationException ae) { // 其他身份验证异常
            msg = "登录异常，请联系管理员！";
        }
        Msg<Object> ret = new Msg<Object>();
        if (loginSuccess) {
            User user=userService.findByUserName(userName);
            // 若登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(userName,String.valueOf(user.getUid()),JwtUtils.SECRET);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            ret.setErrCode(null);
            ret.setMsg(msg);
            return ret;
        } else {
            ret.setErrCode(401);
            ret.setMsg(msg);
            String message="%s"+msg;
            logService.logError(message,userName);
            return ret;
        }

    }

//    /**
//     * 用户退出登录
//     * @return
//     */
//    @GetMapping("/logout")
//    public Msg userLogout(HttpServletRequest request) {
//        Msg ret = new Msg<Object>();
//        ret=userService.userLogoutService(request);
//        return ret;
//    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/registered")
    public Msg registered(@RequestBody User user){
        Msg ret = new Msg();
            int i = userService.addUserInfoService(user);
            ret.setMsg("注册成功");
            return ret;

    }
}
