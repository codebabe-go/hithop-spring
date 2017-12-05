package test.codebabe.microservice.dubbo.main;

import me.codebabe.microservice.dubbo.RpcFramework;
import test.codebabe.microservice.dubbo.HelloService;
import test.codebabe.microservice.dubbo.impl.HelloServiceImpl;

/**
 * RpcProvider
 *
 * @author william.liangf
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}