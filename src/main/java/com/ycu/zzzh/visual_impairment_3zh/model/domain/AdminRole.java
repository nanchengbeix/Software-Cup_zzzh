package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色权限关系表
 * @TableName admin_role
 */
@TableName(value ="admin_role")
@Data
public class AdminRole implements Serializable {
    /**
     * 角色ID
     */
    @TableId
    private Integer rid;

    /**
     * 权限ID
     */
    @TableId
    private Integer pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}