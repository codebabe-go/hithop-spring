package me.codebabe.engine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: code.babe
 * date: 2017-09-12 10:39
 *
 * 给一个方法打出相对应的参数日志, 便于调试
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogLevel {

    enum Level {
        TRACE(0, "trace"),
        DEBUG(1, "debug"),
        INFO(2, "info"),
        WARN(3, "warn"),
        ERROR(4, "error"),
        ;

        Level(int level, String desc) {
            this.level = level;
            this.desc = desc;
        }

        public static Level levelEnum(String desc) {
            for (Level l : values()) {
                if (l.getDesc().equals(desc)) {
                    return l;
                }
            }
            return TRACE;
        }

        private int level;
        private String desc;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    String level() default "debug"; // 默认为debug级别

}
