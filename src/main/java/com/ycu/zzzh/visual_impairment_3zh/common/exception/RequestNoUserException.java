package com.ycu.zzzh.visual_impairment_3zh.common.exception;

/**
 * @ClassName RequestNoUserException
 * @description: TODO 后期将返回false语句全部改为抛出异常
 *
 **/
public class RequestNoUserException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected final String message;

    public RequestNoUserException(String message)
    {
        this.message = message;
    }

    public RequestNoUserException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
