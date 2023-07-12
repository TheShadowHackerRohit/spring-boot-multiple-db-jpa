package com.multipledatasource.postgres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactoryBean",
        basePackages = {"com.multipledatasource.postgres.repo"},
        transactionManagerRef = "secondTransactionManager"
)

public class PostgresDbConfig{


    @Autowired
    private Environment environment;


    //datasource

    @Bean(name = "secondDataSource")
    @Primary
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty("second.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("second.datasource.url"));
        dataSource.setUsername(environment.getProperty("second.datasource.username"));
        dataSource.setPassword(environment.getProperty("second.datasource.password"));

        return dataSource;
    }


    //entityManagerFactory

    @Bean(name = "secondEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

        bean.setDataSource(dataSource());

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);


        Map<String,String> propertiesMap = new HashMap<>();

        propertiesMap.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        propertiesMap.put("hibernate.show_sql","true");
        propertiesMap.put("hibernate.hbm2ddl.auto","update");

        bean.setJpaPropertyMap(propertiesMap);


        bean.setPackagesToScan("com.multipledatasource.postgres.entities");

        return bean;


    }


    //platformTransactionManager
    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();

        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return manager;
    }

}
