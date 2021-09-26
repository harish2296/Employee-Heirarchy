package com.employee.database.employeehierarchy.configuration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Harish Vijaya
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customEntityManagerFactory",
        transactionManagerRef = "customTransactionManager",
        basePackages = "com.employee.database.employeehierarchy.repositories")
public class MysqlConfiguration {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "customDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource customDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "customEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("customDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.employee.database.employeehierarchy.entities")
                .persistenceUnit("test")
                .build();
    }

    @Primary
    @Bean(name = "customTransactionManager")
    public PlatformTransactionManager customerTransactionManager(@Qualifier("customEntityManagerFactory") EntityManagerFactory customerEntityManagerFactory) {
        return new JpaTransactionManager(customerEntityManagerFactory);
    }

}

