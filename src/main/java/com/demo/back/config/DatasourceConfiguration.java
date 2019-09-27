//package com.demo.back.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
///**
// * @ClassName DatasourceConfiguration
// * @Author yakun.shi
// * @Date 2019/6/12 18:01
// * @Version 1.0
// **/
//@Configuration
//public class DatasourceConfiguration {
//    @Bean(name = "dataSource")
//    @Qualifier(value = "dataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "c3p0")
//    public DataSource dataSource()
//    {
//        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
//    }
//}
