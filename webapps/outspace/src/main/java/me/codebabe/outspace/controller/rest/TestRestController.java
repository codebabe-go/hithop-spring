package me.codebabe.outspace.controller.rest;

import me.codebabe.common.response.Response;
import me.codebabe.engine.annotation.LogLevel;
import me.codebabe.engine.annotation.Logging;
import me.codebabe.engine.kafka.CBKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: code.babe
 * date: 2017-09-09 20:33
 */
@RestController
@RequestMapping(value = "/api/outspace/test")
@Logging
public class TestRestController {

    private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

    @LogLevel(level = "info")
    @RequestMapping(value = "/hello")
    public Response test() {
        String welcome = "hello world";
        logger.info("[test]{}", welcome);
        CBKafkaProducer.getInstance().send("test", "key", welcome.getBytes());
        return new Response("xixi");
    }

}
