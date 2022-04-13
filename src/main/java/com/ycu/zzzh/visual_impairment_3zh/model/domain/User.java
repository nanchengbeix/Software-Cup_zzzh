package com.ycu.zzzh.visual_impairment_3zh.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

/**
 * 用户信息
 * @TableName user
 */
@TableName(value ="user")
@Data
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
     * 用户年龄
     */
    private Integer age;

    /**
     * 登录地址
     */
    private String site;

    /**
     * 账号状态:0:正常;1:禁用;2：注销
     */
    private String statu;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}