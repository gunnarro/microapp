package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gunnarro.dietmanager.repository.ActivityRepository;
import com.gunnarro.dietmanager.repository.LogEventRepository;
import com.gunnarro.dietmanager.repository.impl.ActivityRepositoryImpl;
import com.gunnarro.dietmanager.repository.impl.LogEventRepositoryImpl;
import com.gunnarro.useraccount.repository.UserAccountRepository;
import com.gunnarro.useraccount.repository.impl.UserAccountRepositoryImpl;

/**
 * ref:
 * https://egkatzioura.com/2016/04/29/spring-boot-and-database-initialization/
 * 
 * @author admin
 *
 */
@Configuration
@EnableTransactionManagement
public class TestRepositoryConfiguration {

    @Autowired
    @Qualifier(value = "dietManagerDataSource")
    private DataSource dataSource;

    @Bean
    public LogEventRepository logEventRepository() {
        return new LogEventRepositoryImpl(new JdbcTemplate(dataSource));
    }

    @Bean
    public ActivityRepository activityRepository() {
        return new ActivityRepositoryImpl(new JdbcTemplate(dataSource));
    }

    @Bean
    public UserAccountRepository userAccountRepository() {
        return new UserAccountRepositoryImpl(new JdbcTemplate(dataSource));
    }

}
