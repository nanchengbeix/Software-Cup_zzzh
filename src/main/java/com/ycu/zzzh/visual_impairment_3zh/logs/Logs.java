package com.ycu.zzzh.visual_impairment_3zh.logs;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName logs
 */
@TableName(value ="logs")
@Data
public class Logs implements Serializable {
    /**
     * 日志表主键
     */
    @TableId(type = IdType.AUTO)
    private Integer lid;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 日志内容
     */
    private String message;

    /**
     * 日志时间戳
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}