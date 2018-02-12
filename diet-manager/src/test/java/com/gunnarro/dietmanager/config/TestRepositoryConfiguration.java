package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gunnarro.dietmanager.repository.LogEventRepository;
import com.gunnarro.dietmanager.repository.impl.LogEventRepositoryImpl;
import com.gunnarro.useraccount.repository.UserAccountRepository;
import com.gunnarro.useraccount.repository.impl.UserAccountRepositoryImpl;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;

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
        return new LogEventRepositoryImpl(dataSource);
    }
    
    @Bean
    public UserAccountRepository userAccountRepository() {
        return new UserAccountRepositoryImpl(new JdbcTemplate(dataSource));
    }

   }
