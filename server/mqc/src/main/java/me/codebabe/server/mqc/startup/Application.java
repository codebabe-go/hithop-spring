package me.codebabe.server.mqc.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author: code.babe
 * date: 2017-09-27 13:59
 */
@SpringBootApplication
@EnableAsync
@ImportResource({
})
@ComponentScan(basePackages = "me.codebabe.server.mqc")
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Application.class.getClassLoader().getResourceAsStream("properties/application.properties"), "UTF-8"));
            application.setDefaultProperties(props);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        application.setWebEnvironment(false); // 非web工程项目
//        application.setEnvironment(new StandardEnvironment());
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.currentThread().join();
    }
}