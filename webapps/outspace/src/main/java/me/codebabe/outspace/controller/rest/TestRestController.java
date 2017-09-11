package me.codebabe.outspace.controller.rest;

import me.codebabe.common.response.Response;
import me.codebabe.engine.zk.CBZKHolder;
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
public class TestRestController {

    private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

    @RequestMapping(value = "/hello")
    public Response test() {
        logger.info("[test]hello {}", "world");
        // 创建路径
        try {
            if (!CBZKHolder.getInstance().isExist("/persistent")) {
                CBZKHolder.getInstance().createPersistent("/persistent", "directory");
            }
            System.out.println(CBZKHolder.getInstance().getData("/persistent", String.class));

            // 设值
            if (CBZKHolder.getInstance().isExist("/persistent")) {
                CBZKHolder.getInstance().setData("/persistent", "data");
            }
            System.out.println(CBZKHolder.getInstance().getData("/persistent", String.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new Response("xixi");
    }

}
