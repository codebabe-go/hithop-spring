package me.codebabe.engine.annotation.handler;

import me.codebabe.engine.annotation.LogLevel;
import me.codebabe.engine.annotation.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * author: code.babe
 * date: 2017-09-12 17:27
 *
 * 废弃, 这个不是方法栈调用的时候拦截, 在初始化的时候调用
 */
@Deprecated
//@Service("annLoggingHandler")
public class AnnLoggingHandler implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AnnLoggingHandler.class);

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        logging();
    }

    /**
     * 注解的实质性操作
     *
     * @throws Exception
     */
    private void logging() throws Exception{
        final Map<String, Object> logBeans = applicationContext.getBeansWithAnnotation(Logging.class);
        for (final Object logBean : logBeans.values()) {
            final Class<? extends Object> logBeanClz = logBean.getClass();
            for (Method method : logBeanClz.getDeclaredMethods()) {
                LogLevel logLevel = method.getDeclaredAnnotation(LogLevel.class);
                if (logLevel != null) {
                    String level = logLevel.level();
                    Parameter[] parameters = method.getParameters();
                    StringBuilder log = new StringBuilder();
                    log.append(String.format("[%s]", method.getName()));
                    for (Parameter parameter : parameters) {
                        log.append(parameter.toString()).append(", ");
                    }
                    log.substring(0, log.length() - 2);
                    switch (LogLevel.Level.levelEnum(level)) {
                        case TRACE:
                            logger.trace(log.toString());
                            break;
                        case DEBUG:
                            logger.debug(log.toString());
                            break;
                        case INFO:
                            logger.info(log.toString());
                            break;
                        case WARN:
                            logger.warn(log.toString());
                            break;
                        case ERROR:
                            logger.error(log.toString());
                    }
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
