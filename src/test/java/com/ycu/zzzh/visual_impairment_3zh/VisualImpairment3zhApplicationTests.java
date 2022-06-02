package com.ycu.zzzh.visual_impairment_3zh;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VisualImpairment3zhApplicationTests {

    @Test
    public void test01() {

        /*

            入门案例：
                springboot日志具体实现
                    级别测试
                    默认是info级别
                    logback的风格输出（默认使用的是logback的日志实现）


         */
        Logger logger = LoggerFactory.getLogger(VisualImpairment3zhApplicationTests.class);
        logger.error("error信息");
        logger.warn("warn信息");
        logger.info("info信息");
        logger.debug("debug信息");
        logger.trace("trace信息");

    }

}
