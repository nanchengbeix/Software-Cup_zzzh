package com.ycu.zzzh.visual_impairment_3zh.EventBus;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.ycu.zzzh.visual_impairment_3zh.EventBus.Configures.DotnetCapConfig;
import com.ycu.zzzh.visual_impairment_3zh.EventBus.Events.Event;
import com.ycu.zzzh.visual_impairment_3zh.EventBus.Events.UserCreatedSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName EventBusServiceImpl
 * @description: TODO
 * @Date 2022/5/23 18:03
 * @Version 1.0
 **/
public class DotnetCapEventBusServiceImpl implements EventBusService {
    private static Logger logger = LoggerFactory.getLogger(DotnetCapEventBusServiceImpl.class);
    // TODO 在配置文件配置好 然后注入这个config 记得删掉这个new

    private DotnetCapConfig config=new DotnetCapConfig();
    private Connection conn;
    private Channel channel;
    public DotnetCapEventBusServiceImpl() throws IOException, TimeoutException {
        buildDotnetCapBus();
    }

    private void buildDotnetCapBus() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(config.userName);
        factory.setPassword(config.password);
        factory.setVirtualHost(config.virtualHost);
        factory.setHost(config.hostName);
        factory.setPort(config.port);

        conn = factory.newConnection();
        channel = conn.createChannel();
    }


    @Override
    public <TEvent extends Event> void send(TEvent event) throws IOException {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("cap-msg-id",  UUID.randomUUID().toString());
        String eventName = event.getClass().getSimpleName();
        headers.put("cap-msg-name", eventName);
        String eventContent = JSONObject.toJSONString(event);
        channel.basicPublish(
                config.exchangesName,
                eventName,
                new AMQP.BasicProperties.Builder()
                        .headers(headers)
                        .build(),
                eventContent.getBytes(StandardCharsets.UTF_8));


//        amqpTemplate.convertAndSend("cap.default.router",eventName,headers);
        logger.info("发送事件" + eventName + ": " + eventContent );
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        EventBusService eventBus = new DotnetCapEventBusServiceImpl();
        UserCreatedSuccessEvent event = new UserCreatedSuccessEvent();
        event.userId = "123123123";
        eventBus.send(event);
    }
}


