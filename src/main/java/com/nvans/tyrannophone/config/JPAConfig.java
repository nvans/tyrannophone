package com.nvans.tyrannophone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.nvans.tyrannophone.model" })
@Import(DaoBeans.class)
@PropertySource(value = "classpath:application.properties")
public class JPAConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();

        hibernateProp.put("hibernate.dialect", Objects.requireNonNull(env.getProperty("hibernate.dialect")));
        hibernateProp.put("hibernate.format_sql", Objects.requireNonNull(env.getProperty("hibernate.format_sql")));
        hibernateProp.put("hibernate.hbm2ddl.auto", Objects.requireNonNull(env.getProperty("hibernate.hbm2ddl.auto")));
        hibernateProp.put("hibernate.use_sql_comments", Objects.requireNonNull(env.getProperty("hibernate.use_sql_comments")));
        hibernateProp.put("hibernate.show_sql", Objects.requireNonNull(env.getProperty("hibernate.show_sql")));
        hibernateProp.put("hibernate.max_fetch_depth", Objects.requireNonNull(env.getProperty("hibernate.max_fetch_depth")));
        hibernateProp.put("hibernate.jdbc_batch_size", Objects.requireNonNull(env.getProperty("hibernate.jdbc_batch_size")));
        hibernateProp.put("hibernate.jdbc.fetch_size", Objects.requireNonNull(env.getProperty("hibernate.jdbc.fetch_size")));

        return hibernateProp;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan("com.nvans.tyrannophone.model");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();

        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

//    @Bean
//    public GenericDao<User> userDao() {
//        return new GenericDaoImpl<>(User.class);
//    }
}
