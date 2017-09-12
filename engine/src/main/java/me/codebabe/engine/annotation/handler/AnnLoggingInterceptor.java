package me.codebabe.engine.annotation.handler;

import me.codebabe.engine.annotation.LogLevel;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * author: code.babe
 * date: 2017-09-12 19:52
 *
 * 动态代理来实现注解日志
 */
public class AnnLoggingInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AnnLoggingInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            Method method = methodInvocation.getMethod();
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
        } catch (Exception e) {
        }
        return methodInvocation.proceed();
    }
}
