package me.codebabe.outspace.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author: code.babe
 * date: 2017-01-13 11:43
 */
@SpringBootApplication
@EnableAsync
@ImportResource({
        "classpath:config/hithop-outspace.xml",
})
@ComponentScan(basePackages = "me.codebabe.outspace")
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Application.class.getClassLoader().getResourceAsStream("properties/application.properties"), "UTF-8"));
            application.setDefaultProperties(props);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        application.run(args);
    }
}
