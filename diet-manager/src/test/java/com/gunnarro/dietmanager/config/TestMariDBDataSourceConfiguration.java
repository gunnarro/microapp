package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

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
public class TestMariDBDataSourceConfiguration {

    @Value("${mariadb.jdbc.url}")
    private String jdbcUrl;

    @Value("${mariadb.jdbc.user}")
    private String jdbcUser;

    @Value("${mariadb.jdbc.pwd}")
    private String jdbcPwd;

    @Bean
    @Qualifier(value = "pwdEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public MariaDB4jSpringService mariaDB4jSpringService() {
        return new MariaDB4jSpringService();
    }

    @Bean
    @Qualifier(value = "dietManagerDataSource")
    @Primary
    public DataSource dietManagerDataSource(MariaDB4jSpringService mariaDB4jSpringService) throws ManagedProcessException {
        // Create our database with default root user and no password
        mariaDB4jSpringService.getDB().createDB("dietmanager-unittest");
        DBConfigurationBuilder config = mariaDB4jSpringService.getConfiguration();
        DataSource ds = DataSourceBuilder.create().username("root").password("").url(config.getURL("dietmanager-unittest"))
                .driverClassName("org.mariadb.jdbc.Driver").build();

        DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
        return ds;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }

    private DatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("schema.sql"));
        databasePopulator.addScript(new ClassPathResource("data.sql"));
        return databasePopulator;
    }
}
