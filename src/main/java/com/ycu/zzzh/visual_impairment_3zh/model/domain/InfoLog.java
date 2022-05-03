package com.ycu.zzzh.visual_impairment_3zh.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName info_log
 */
@TableName(value ="info_log")
@Data
public class InfoLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer logId;

    /**
     * 日期
     */
    @TableId
    private Date createTime;

    /**
     * 日志信息所在类
     */
    private String logClass;

    /**
     * Log级别 info warndebug error等
     */
    private String logLevel;

    /**
     * Log信息
     */
    private String logMessage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}