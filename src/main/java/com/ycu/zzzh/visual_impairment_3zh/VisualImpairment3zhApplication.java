package com.ycu.zzzh.visual_impairment_3zh;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.ycu.zzzh.visual_impairment_3zh.mapper")
public class VisualImpairment3zhApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisualImpairment3zhApplication.class, args);

    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                // 放开某些字符（反斜杠）限制
                connector.setProperty("relaxedQueryChars", "\\");
            }
        });
        return factory;
    }


}
