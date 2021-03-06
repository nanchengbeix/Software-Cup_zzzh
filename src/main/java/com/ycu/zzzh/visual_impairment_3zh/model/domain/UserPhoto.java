package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName user_photo
 */
@TableName(value ="user_photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoto implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer uid;

    /**
     * 
     */
    private String fileMkdirs;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}