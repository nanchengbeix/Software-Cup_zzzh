package com.ycu.zzzh.visual_impairment_3zh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer userid;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String pwd;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}