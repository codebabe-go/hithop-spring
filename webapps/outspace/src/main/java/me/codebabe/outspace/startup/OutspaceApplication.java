package me.codebabe.outspace.startup;

import me.codebabe.engine.zk.CBZKHolder;
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
@ComponentScan(basePackages = {"me.codebabe.outspace", "me.codebabe.dao"})
public class OutspaceApplication {

    private static final Logger logger = LoggerFactory.getLogger(OutspaceApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(OutspaceApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(OutspaceApplication.class.getClassLoader().getResourceAsStream("properties/application.properties"), "UTF-8"));
            application.setDefaultProperties(props);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        application.run(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> { // 退出的时候, 注意删除自己的这个目录, 对历史数据有影响?
            try {
                CBZKHolder.getInstance().deleteRNode("/".concat(props.getProperty("app.name")));
                logger.info("servlet is shutdown");
            } catch (Exception e) {
            }
        }));
    }
}
