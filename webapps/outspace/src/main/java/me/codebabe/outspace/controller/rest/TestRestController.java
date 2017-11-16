package me.codebabe.outspace.controller.rest;

import me.codebabe.common.exception.HithopException;
import me.codebabe.common.response.Response;
import me.codebabe.dao.mysql.dao.UserDAO;
import me.codebabe.dao.mysql.model.User;
import me.codebabe.engine.annotation.LogLevel;
import me.codebabe.engine.annotation.Logging;
import me.codebabe.engine.kafka.CBKafkaProducer;
import me.codebabe.outspace.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserFacade userFacade;

    @LogLevel(level = "info")
    @RequestMapping(value = "/hello")
    public Response test() {
        String welcome = "hello world";
        logger.info("[test]{}", welcome);
        CBKafkaProducer.getInstance().send("test", "key", welcome.getBytes());
        return new Response("xixi");
    }

    @RequestMapping(value = "/insert")
    public Response testMybatis() {
        try {
            User user = new User();
            user.setName("cb");
            user.setEmail("codebabe.go@gmail.com");
            userFacade.insert(user);
        } catch (HithopException e) {
            return new Response(e.getCode(), e.getMessage());
        }
        return new Response("xixi");
    }

}
