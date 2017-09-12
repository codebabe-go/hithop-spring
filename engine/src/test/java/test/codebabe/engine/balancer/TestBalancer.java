package test.codebabe.engine.balancer;

import org.junit.Test;

/**
 * author: code.babe
 * date: 2017-09-12 16:02
 */
public class TestBalancer {

    @Test
    public void testIP() {
        String localIp = System.getProperty("node.ip");
        System.out.println(localIp);
    }

}
