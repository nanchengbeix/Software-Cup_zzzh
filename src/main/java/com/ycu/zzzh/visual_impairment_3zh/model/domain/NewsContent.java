package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName news_content
 */
@TableName(value ="news_content")
@Data
public class NewsContent implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 新闻内容字数
     */
    private Integer words;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}