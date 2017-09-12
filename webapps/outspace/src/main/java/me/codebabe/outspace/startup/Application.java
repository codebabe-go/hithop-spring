package me.codebabe.outspace.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * author: code.babe
 * date: 2017-01-13 11:43
 */
@SpringBootApplication
//@ImportResource({
//})
@EnableAsync
@ImportResource({
        "classpath:config/hithop-outspace.xml",
})
@ComponentScan(basePackages = "me.codebabe.outspace")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
