package test.codebabe.microservice.dubbo.impl;

import test.codebabe.microservice.dubbo.HelloService;

/**
 * HelloServiceImpl
 *
 * @author william.liangf
 */
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "Hello " + name;
    }

}