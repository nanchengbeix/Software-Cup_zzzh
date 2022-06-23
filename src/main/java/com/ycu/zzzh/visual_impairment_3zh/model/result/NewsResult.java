package com.ycu.zzzh.visual_impairment_3zh.model.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName NewsResult
 * @description: TODO
 * @Date 2022/4/29 13:21
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResult {
    /**
     *新闻id
     */
    private Integer id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 新闻内容字数
     */
    private Integer words;

    /**
     * 分类名称
     */
    private String tagName;

    /**
     * 浏览次数
     */
    private Integer viewsNum;

    /**
     * 新闻收藏数
     */
    private Integer collectNum;

    /**
     * 新闻发布时间
     */
    private Date createdTime;

    /**
     * 预留字段
     */
    private String pre;

}
