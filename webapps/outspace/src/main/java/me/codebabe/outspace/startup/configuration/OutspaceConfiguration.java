package me.codebabe.outspace.startup.configuration;

import com.google.common.collect.Lists;
import me.codebabe.engine.config.ConfigLoader;
import me.codebabe.engine.config.DefaultPropertiesLoader;
import me.codebabe.engine.config.GlobalConfig;
import me.codebabe.engine.config.ZKConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: code.babe
 * date: 2017-09-11 20:41
 */
@Configuration
public class OutspaceConfiguration {

    @Bean
    public ZKConfigLoader zkConfigLoader() {
        ZKConfigLoader loader = new ZKConfigLoader("outspace");
        loader.check();
        return loader;
    }

    @Bean
    public DefaultPropertiesLoader defaultPropertiesLoader() {
        DefaultPropertiesLoader loader = new DefaultPropertiesLoader("classpath:properties/application.properties");
        loader.check();
        return loader;
    }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig(Lists.<ConfigLoader>newArrayList(zkConfigLoader(), defaultPropertiesLoader()));
        globalConfig.check();
        return globalConfig;
    }
}
