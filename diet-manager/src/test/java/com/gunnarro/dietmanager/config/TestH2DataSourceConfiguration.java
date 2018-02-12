package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
//@Configuration
//@EnableTransactionManagement
public class TestH2DataSourceConfiguration {

//    // @Autowired
//    // private StandardPBEStringEncryptor pwdEncoder;
//
//    @Value("${jdbc.url}")
//    private String jdbcUrl;
//
//    @Value("${jdbc.user}")
//    private String jdbcUser;
//
//    @Value("${jdbc.pwd}")
//    private String jdbcPwd;
//
//    @Bean
//    @Qualifier(value = "pwdEncoder")
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(13);
//    }
//
//    @Bean
//    @Qualifier(value = "dietManagerDataSource")
//    @Primary
//    public DataSource dietManagerDataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName("org.h2.Driver");
//        ds.setUrl(jdbcUrl);
//        ds.setUsername(jdbcUser);
//        ds.setPassword(jdbcPwd);
//        // if (jdbcPwd == null){
//        // throw new RuntimeException("jdbc pwd not set! pwd: " + jdbcPwd + ",
//        // encoded: " + pwdEncoder.decrypt(jdbcPwd));
//        // }
//        // ds.setPassword(pwdEncoder.decrypt((jdbcPwd)));
//        // ds.setUrl(environment.getRequiredProperty("spring.datasource.url"));
//        // ds.setUsername(environment.getRequiredProperty("spring.datasource.username"));
//        // ds.setPassword(environment.getRequiredProperty("spring.datasource.password"));
//        DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
//        return ds;
//    }
//
//    @Bean
//    @Qualifier(value = "logEventDataSource")
//    // @Primary
//    public DataSource logEventDataSource() {
//        return dietManagerDataSource();
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(logEventDataSource());
//    }
//
//    @Bean
//    public UserAccountRepository userAccountRepository() {
//        return new UserAccountRepositoryImpl(new JdbcTemplate(dietManagerDataSource()));
//    }
//
//    private DatabasePopulator createDatabasePopulator() {
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//        databasePopulator.setContinueOnError(true);
//        databasePopulator.addScript(new ClassPathResource("schema.sql"));
//        databasePopulator.addScript(new ClassPathResource("data.sql"));
//        return databasePopulator;
//    }
}
