package me.codebabe.server.mqc.startup;

import me.codebabe.server.mqc.consumer.ConsumerQueue;
import me.codebabe.server.mqc.consumer.kafka.KafkaConsumerHelper;
import me.codebabe.server.mqc.processor.PrintProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
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
 * date: 2017-09-27 13:59
 */
@SpringBootApplication
@EnableAsync
@ImportResource({
})
@ComponentScan(basePackages = "me.codebabe.server.mqc")
public class MqcApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MqcApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MqcApplication.class);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(MqcApplication.class.getClassLoader().getResourceAsStream("properties/application.properties"), "UTF-8"));
            application.setDefaultProperties(props);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        application.setWebEnvironment(false); // 非web工程项目
//        application.setEnvironment(new StandardEnvironment());
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    // 自定义守护进程, 消费者
    @Override
    public void run(String... args) throws Exception {
        KafkaConsumerHelper helper = new KafkaConsumerHelper();
        helper.register(new ConsumerQueue(1, "test"), new PrintProcessor());
        helper.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            helper.shutdown();
            logger.info("shutdown finish");
        }));
    }
}