package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 新闻作者表
 * @TableName news_author
 */
@TableName(value ="news_author")
@Data
public class NewsAuthor implements Serializable {
    /**
     * 作者id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 作者名称
     */
    private String author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}