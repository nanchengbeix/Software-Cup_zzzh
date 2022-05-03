package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.ycu.zzzh.visual_impairment_3zh.model.result.PageResult;
import com.ycu.zzzh.visual_impairment_3zh.model.result.ToResult;
import com.ycu.zzzh.visual_impairment_3zh.model.domain.User;
import com.ycu.zzzh.visual_impairment_3zh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserManageController
 * @description: 对用户信息进行管理
 * @Date 2022/4/8 11:30
 * @Version 1.0
 **/
@RestController
@RequestMapping("user")
public class UserManageController {
    @Autowired
    private UserService userService;

    /**
     * 声明单元方法：新增用户
     */
    @RequestMapping("/addUserInfo")
    public ToResult addUserInfo(User user){
        //处理请求
        int insert=userService.addUserInfoService(user);
        return new ToResult("1".equals(insert+""),"");
    }
    /**
     * 声明单元方法：根据条件查询用户
     */
    @RequestMapping("selUserInfo")
    public PageResult<User> selUserInfo(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows,
            String username,String status,String phone){
        //处理请求
        PageResult<User> pageResult=userService.selUserInfoService(page,rows,username,status,phone);
        //响应结果
        return pageResult;
    }

    /**
     *声明单元方法：更新用户信息
     *
     */
    //TODO 前端进行修改之前先将数据填充到表格中，使得管理员不修改的属性仍然传递给后端原来的属性
    @RequestMapping("/userEdit")
    public ToResult userEdit(User user){
        //处理请求
        boolean insert=userService.updateById(user);
        //响应结果
        return new ToResult(insert,insert?"更新成功":"更新失败");
    }

    /**
     * 声明单元方法：更新用户账户状态(禁用/启用)
     *
     */
    @RequestMapping("/userStatus")
    public ToResult userStatus(String uids,String statuss){
        //处理请求
        //获取要更改的用户ID的数组
        String[] uidstr = uids.split(",");
        //获取要修改的用户当前的账号状态数组
        String[] statusstr = statuss.split(",");
        User user=new User();
        //修改用户账号状态
        for (int i=0;i<uidstr.length;i++){
            user.setUid(Integer.valueOf(uidstr[i]));
            user.setStatus("0".equals(statusstr[i])?"0":"1");
            userService.updateById(user);
        }
        //响应结果
        return new ToResult(true,"");
    }




}
