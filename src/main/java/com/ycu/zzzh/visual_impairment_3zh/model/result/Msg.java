package com.ycu.zzzh.visual_impairment_3zh.model.result;

import java.io.Serializable;

public class Msg<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int errCode;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public Msg() {
        super();
    }

    public Msg(int errCode, String msg, T data) {
        super();
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
