package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户人脸表
 * @TableName user_face
 */
@TableName(value ="user_face")
@Data
public class UserFace implements Serializable {
    /**
     * 人脸库返回的uuid
     */
    private String id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 人脸id(Json)
     */
    private String imageIds;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}