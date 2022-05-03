package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName news_sort
 */
@TableName(value ="news_sort")
@Data
public class NewsSort implements Serializable {
    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String tagName;

    /**
     * 分类对应的本地图标的名字
     */
    private String tagIco;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}