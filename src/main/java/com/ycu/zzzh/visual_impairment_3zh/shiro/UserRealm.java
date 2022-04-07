package com.ycu.zzzh.visual_impairment_3zh.shiro;

import com.ycu.zzzh.visual_impairment_3zh.model.User;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName UserRealm
 * @description: TODO
 * @Date 2022/4/7 19:50
 * @Version 1.0
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //自定义认证策略
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //声明认证代码
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //2.根据用户名获取数据库中的用户信息
        User user = userService.findByUserName(token.getUsername());
        //3.认证
        if(user!=null){//用户名是正确的
            //4.认证密码
            AuthenticationInfo info=  new SimpleAuthenticationInfo(token.getUsername(),user.getPwd(), ByteSource.Util.bytes(user.getUsername()),user.getUsername());
            return info;
        }
        return null;
    }
}
