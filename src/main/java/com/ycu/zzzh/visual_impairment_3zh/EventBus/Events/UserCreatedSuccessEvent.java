package com.ycu.zzzh.visual_impairment_3zh.EventBus.Events;

import lombok.Data;

/**
 * @ClassName UserCreatedSuccessEvent
 * @description: TODO
 * @Date 2022/5/24 21:19
 * @Version 1.0
 **/
public class UserCreatedSuccessEvent implements Event {
    public String userId;
//    public byte[] fileBytes;
}
