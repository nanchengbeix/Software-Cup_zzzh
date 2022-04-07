package com.ycu.zzzh.visual_impairment_3zh.config;


import com.ycu.zzzh.visual_impairment_3zh.shiro.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    //声明MyRealmBean
    @Bean
    public UserRealm userRealm(){
     return new UserRealm();
    }
    //声明bean方法
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //创建凭证匹配器
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        //设置匹配器的加密算法
        matcher.setHashAlgorithmName("md5");
        //设置匹配器的迭代加密次数:2次
        matcher.setHashIterations(2);
        //将匹配器注入到自定义的认证策略对象中
        userRealm.setCredentialsMatcher(matcher);
        //将自定义的认证策略对象注入到SecurityManager
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")
                                                         DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        return factoryBean;
    }


    //自定义shiro过滤器参数bean
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/login", "anon");
        definition.addPathDefinition("/userLogin", "anon");
        //开启shiro内置的退出过滤器，完成退出功能
        definition.addPathDefinition("/logout", "logout");
        //definition.addPathDefinition("/main", "anon");
        definition.addPathDefinition("/**", "user");
        return definition;
    }


}
