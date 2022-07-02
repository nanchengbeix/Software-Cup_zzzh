package com.ycu.zzzh.visual_impairment_3zh.model.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @JsonInclude(value= JsonInclude.Include.NON_NULL)
    private Integer errCode;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 返回数据
     */
    @JsonInclude(value= JsonInclude.Include.NON_NULL)
    private T data;

    public void efound(Integer errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }
}
