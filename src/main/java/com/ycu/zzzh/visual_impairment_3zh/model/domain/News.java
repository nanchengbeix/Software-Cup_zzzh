package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName news
 */
@TableName(value ="news")
@Data
public class News implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻分类标签
     */
    private Integer tag;

    /**
     * 浏览次数
     */
    private Integer viewsNum;

    /**
     * 新闻评论数
     */
    private Integer commentNum;

    /**
     * 新闻内容id
     */
    private String contentId;

    /**
     * 新闻出版者
     */
    private Integer creatorId;

    /**
     * 新闻发布时间
     */
    private Date createdTime;

    /**
     * 热词
     */
    private String rawKeyWords;

    @TableField(exist = false)
    @Setter
    @Getter
    private NewsContent newsContent;

    @TableField(exist = false)
    @Setter
    @Getter
    private  String author;

    @TableField(exist = false)
    @Setter
    @Getter
    private String tagName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}