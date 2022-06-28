package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_collections
 */
@TableName(value ="user_collections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollections implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Long userid;

    /**
     * 
     */
    private Long newid;

    /**
     * 
     */
    private Date curtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}