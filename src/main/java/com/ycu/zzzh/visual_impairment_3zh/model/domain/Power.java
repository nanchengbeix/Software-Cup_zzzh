package com.ycu.zzzh.visual_impairment_3zh.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 权限信息
 * @TableName power
 */
@TableName(value ="power")
@Data
public class Power implements Serializable {
    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Integer pid;

    /**
     * 权限名称
     */
    private String pname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}