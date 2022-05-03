package com.ycu.zzzh.visual_impairment_3zh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ycu.zzzh.visual_impairment_3zh.mapper")
public class VisualImpairment3zhApplication {

    public static void main(String[] args) {
            SpringApplication.run(VisualImpairment3zhApplication.class, args);
    }

}
