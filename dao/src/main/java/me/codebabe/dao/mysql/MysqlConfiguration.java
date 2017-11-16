package me.codebabe.dao.mysql;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * author: code.babe
 * date: 2017-11-16 14:25
 */
@Configuration
public class MysqlConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(MysqlConfiguration.class);

    @Bean(name = "defaultDataSource")//, destroyMethod = "close")
    public DataSource defaultDataSource() {
        Properties props = new Properties();
        BasicDataSource dataSource = new BasicDataSource();
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream("properties/jdbc.properties"));
            dataSource.setDriverClassName(props.getProperty("jdbc.driver", "com.mysql.jdbc.Driver"));
            dataSource.setUrl(props.getProperty("jdbc.url", "jdbc:mysql://127.0.0.1:3306/cb?characterEncoding=UTF-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull"));
            dataSource.setUsername(props.getProperty("jdbc.username", "root"));
            dataSource.setPassword(props.getProperty("jdbc.password", ""));
            dataSource.setInitialSize(Integer.parseInt(props.getProperty("dbcp2.initial.size", "1")));
            dataSource.setMaxTotal(Integer.parseInt(props.getProperty("dbcp2.max.total", "10")));
            dataSource.setMaxIdle(Integer.parseInt(props.getProperty("dbcp2.max.idle", "5")));
            dataSource.setMinIdle(Integer.parseInt(props.getProperty("dbcp2.min.idle", "1")));
            dataSource.setDefaultAutoCommit(Boolean.parseBoolean(props.getProperty("dbcp2.default.auto.commit", "false")));
            dataSource.setPoolPreparedStatements(Boolean.parseBoolean(props.getProperty("dbcp2.pool.prepared.statements", "true")));
            dataSource.setMaxOpenPreparedStatements(Integer.parseInt(props.getProperty("dbcp2.max.open.prepared.statements", "50")));
            dataSource.setRemoveAbandonedTimeout(Integer.parseInt(props.getProperty("dbcp2.remove.abandoned.timeout", "180")));
            dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(props.getProperty("dbcp2.time.between.eviction.runs.millis", "60000")));
            dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(props.getProperty("dbcp2.min.evictable.idle.time.millis", "1800000")));
            dataSource.setValidationQuery(props.getProperty("dbcp2.validation.query", "select 1"));
            dataSource.setTestOnBorrow(Boolean.parseBoolean(props.getProperty("dbcp2.test.on.borrow", "false")));
            dataSource.setTestWhileIdle(Boolean.parseBoolean(props.getProperty("dbcp2.test.while.idle", "true")));
            dataSource.setTestOnReturn(Boolean.parseBoolean(props.getProperty("dbcp2.test.while.return", "false")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    @Bean
    public MapperScannerConfigurer mapperFactoryBeans() {
        MapperScannerConfigurer mapperFactoryBeans = new MapperScannerConfigurer();
        mapperFactoryBeans.setBasePackage("me.codebabe.dao.mysql.dao");

        mapperFactoryBeans.setSqlSessionFactoryBeanName("defaultSqlSessionFactoryBean");
        return mapperFactoryBeans;
    }

    @Bean(name = "defaultSqlSessionFactoryBean")
    public SqlSessionFactoryBean defaultSqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        } catch (IOException e) {
            logger.error("Load mybatis mapper error", e);
        }
        sqlSessionFactoryBean.setTypeAliasesPackage("me.codebabe.dao.mysql.model");
        sqlSessionFactoryBean.setDataSource(defaultDataSource());

        org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
        conf.setUseGeneratedKeys(true);

        conf.setCacheEnabled(false);
        conf.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
        conf.setMapUnderscoreToCamelCase(true);
        conf.setUseGeneratedKeys(true);
        conf.setDefaultStatementTimeout(30);// TODO: 这里控制查询超时30秒
        conf.setDefaultFetchSize(100);
        sqlSessionFactoryBean.setConfiguration(conf);
        return sqlSessionFactoryBean;
    }

    @Bean(name = "defaultPlatformTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(defaultDataSource());
    }

}
