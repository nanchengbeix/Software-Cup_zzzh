package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName news_count
 */
@TableName(value ="news_count")
@Data
public class NewsCount implements Serializable {
    /**
     * 新闻id
     */
    @TableId
    private Integer nid;

    /**
     * 新闻阅读量
     */
    private Integer viewsNum;

    /**
     * 新闻收藏量
     */
    private Integer collectNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}