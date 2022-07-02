package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @TableName user
 */
@TableName(value ="user")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户昵称
     */
    //自定义注解：昵称不能重复
    @Size(max = 20, message = "用户名不能超过10个字符")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户名限制使用非法字符")
    private String nickname;

    /**
     * 用户感兴趣分类
     */
    @NotBlank(message = "请选择你喜欢的新闻类别")
    private String tags;

    /**
     * 用户手机号码
     */
    @NotBlank(message = "请输入手机号")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}",message = "手机格式有误")
    private String phone;

    /**
     * 用户密码
     */
    @NotBlank(message = "请输入密码")
    @Size(min = 6,max = 16,message = "请输入8-16位密码")
    private String pwd;

    /**
     * 用户生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    /**
     * 登录地址
     */
    private String site;

    /**
     * 账号状态:0:正常;1:禁用;2：注销
     */
    private String status;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除时间
     */
    private Date deletedTime;

    /**
     * 修改时间
     */
    private Date lastmodifidTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}