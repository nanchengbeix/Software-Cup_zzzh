//package com.ycu.zzzh.visual_impairment_3zh.test;
//
//
//import com.ycu.zzzh.visual_impairment_3zh.utils.DateUtils;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;
//
///**
// * @ClassName TestLog4j
// * @description: TODO
// * @Date 2022/4/11 17:17
// * @Version 1.0
// **/
//
//public class TestLog4j {
//  public static final Logger LOGGER= LoggerFactory.getLogger(TestLog4j.class);
//    @Test
//    public void TestLog4j(){
//        //打印日志信息
//        LOGGER.error("error");
//        LOGGER.warn("warn");
//        LOGGER.info("info");
//        LOGGER.debug("debug");
//        LOGGER.trace("trace");
//        // 使用占位符输出日志信息
//        String name = "lucy";
//        Integer age = 18;
//        LOGGER.info("{}今年{}岁了！", name, age);
//        // 将系统异常信息写入日志
//        try {
//            int i = 1 / 0;
//        } catch (Exception e) {
//            // e.printStackTrace();
//            LOGGER.info("出现异常：", e);
//        }
//    }
//    @Test
//    public void TestLocalDateForDate(){
//        //加密密码
//        String password =null;
//          SimpleHash simpleHash= new SimpleHash( "SHA-1", "123456", "admin", 16);
//        System.out.println(simpleHash.toString());
//
//
//    }
//}
