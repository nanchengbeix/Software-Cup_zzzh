package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
    private Integer contentId;

    /**
     * 新闻出版者
     */
    private Integer creatorId;

    /**
     * 新闻创建时间
     */
    private Date createdTime;

    /**
     * 预留字段
     */
    private String pre;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}