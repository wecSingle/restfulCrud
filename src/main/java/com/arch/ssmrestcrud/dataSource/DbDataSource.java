package com.arch.ssmrestcrud.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 用于管理Mybatis的SqlSession
 *
 * 使用@MapperScan来扫描注册mybatis数据库接口类
 * 其中basePackages属性表明接口类所在的包，sqlSessionTemplateRef表明接口类使用的SqlSessionTemplate
 */
@Configuration
@MapperScan(basePackages = "com.arch.ssmrestcrud.dao", sqlSessionTemplateRef = "dbSqlSessionTemplate")
public class DbDataSource {

    /**
     * @ConfigurationProperties用于转载properties/yml配置文件
     * @return
     */
    @Bean(name = "dbData")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbSqlSessionFactory")
    public SqlSessionFactory dbSqlSessionFactory(@Qualifier("dbData") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "dbSqlSessionTemplate")
    public SqlSessionTemplate dbSqlSessionTemplate(@Qualifier("dbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "dbTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dbData") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
