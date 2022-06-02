package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 用户名称
     */
    private String username;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户密码
     */
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