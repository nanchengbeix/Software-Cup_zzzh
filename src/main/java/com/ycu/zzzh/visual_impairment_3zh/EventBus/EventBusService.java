package com.ycu.zzzh.visual_impairment_3zh.EventBus;

import com.ycu.zzzh.visual_impairment_3zh.EventBus.Events.Event;

import java.io.IOException;

public interface EventBusService {
    <TEvent extends Event> void send(TEvent event) throws IOException;
}
