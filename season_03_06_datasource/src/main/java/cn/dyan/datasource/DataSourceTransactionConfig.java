package cn.dyan.datasource;

import cn.dyan.interceptor.AnnotationMyTransactionAttributeSource;
import cn.dyan.interceptor.MyTransactionAttributeSource;
import cn.dyan.interceptor.MyTransactionInterceptor;
import cn.dyan.tx.PlatformMyTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

/**
 * Created by demi on 2017/9/17.
 */
@Configuration
public class DataSourceTransactionConfig {

    @Bean
    public PlatformMyTransactionManager transactionManager(){
        DataSourceMyTransactionManager transactionManager = new DataSourceMyTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    @Order(1)
    public MyTransactionAttributeSource transactionAttributeSource(){
        return new AnnotationMyTransactionAttributeSource();
    }

    @Bean
    public MyTransactionInterceptor transactionInterceptor(){
        MyTransactionInterceptor interceptor = new MyTransactionInterceptor();
        interceptor.setTransactionAttributeSource(transactionAttributeSource());
        interceptor.setTxManager(transactionManager());
        return  interceptor;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/mytx?characterEncoding=utf8&useSSL=true");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("Root!123");
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return  new JdbcTemplate(dataSource());
    }

}
