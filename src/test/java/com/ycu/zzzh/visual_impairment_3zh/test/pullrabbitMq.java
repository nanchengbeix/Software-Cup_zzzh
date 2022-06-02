//package com.ycu.zzzh.visual_impairment_3zh.test;
//
//import com.rabbitmq.client.BuiltinExchangeType;
//import com.ycu.zzzh.visual_impairment_3zh.utils.RabbitMqUtils;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
///**
// * @ClassName pullrabbitMq
// * @description: TODO
// * @Date 2022/5/23 20:37
// * @Version 1.0
// **/
//public class pullrabbitMq {
//    @Test
//    public void pullMqTest() throws IOException, TimeoutException {
//        RabbitMqUtils rabbitMqUtils2 = new RabbitMqUtils("152.136.109.47", 5672, "Admin", "Admin123123@", "/");
//        System.out.println("接收topic消息");
//        rabbitMqUtils2.pullMq("amq.topic", "test-topic", "topic-key", BuiltinExchangeType.TOPIC, (record, body) -> {
//            String message = new String(body, "UTF-8");
//            System.out.println(message);
//        });
//        rabbitMqUtils2.close();
//    }
//}
