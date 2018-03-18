package com.gunnarro.dietmanager.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
public class DataSourceConfiguration {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.pwd}")
    private String jdbcPwd;

    // bean is singleton as default
    @Bean(name = "dietManagerDataSource")
    @Primary
    public DataSource dietManagerDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(jdbcUrl);
        ds.setUsername(jdbcUser);
        ds.setPassword(jdbcPwd);
        Properties p = new Properties();
        p.put("useSSL", "false");
        ds.setConnectionProperties(p);
        runUpdateDBScript(ds);
        return ds;
    }

    private void runUpdateDBScript(DataSource ds) {
        try {
            DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error init datasource: " + e.getMessage());
        }
    }

    private ResourceDatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("update.sql"));
        return databasePopulator;
    }

    /**
     * same as dietmanager data source
     * 
     * @return
     */
    @Bean(name = "logEventDataSource")
    // @Primary
    public DataSource logEventDataSource() {
        return dietManagerDataSource();
    }

    @Bean(name = "activityDataSource")
    public DataSource activityDataSource() {
        return dietManagerDataSource();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dietManagerDataSource());
    }

    @Bean
    public UserAccountRepository userAccountRepository() {
        return new UserAccountRepositoryImpl(new JdbcTemplate(dietManagerDataSource()));
    }
}
