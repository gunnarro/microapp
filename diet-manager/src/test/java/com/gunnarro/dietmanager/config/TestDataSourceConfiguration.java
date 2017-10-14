package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

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
public class TestDataSourceConfiguration {

	/**
	 * <property name="driverClassName" value="org.h2.Driver" />
		<!-- <property name="url" value="jdbc:h2:dietmanager_unit_test" /> -->
		<property name="url" value="jdbc:h2:mem:dietmanager_unit_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;IGNORECASE=TRUE" />
		<property name="username" value="SA" />
		<property name="password" value="" />
	 * @return
	 */
    @Bean(name = "logEventDataSource")
    @Primary
    public DataSource createLogEventDataSource() {
    	DriverManagerDataSource ds = new DriverManagerDataSource();
    	ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:dietmanager_unit_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;IGNORECASE=TRUE");
        ds.setUsername("SA");
        ds.setPassword("");
        DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
//        ds.setUrl(environment.getRequiredProperty("spring.datasource.url"));
//        ds.setUsername(environment.getRequiredProperty("spring.datasource.username"));
//        ds.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return ds;
    }
    
    @Bean(name = "dietManagerDataSource")
//  @Primary
  public DataSource createDietManagerDataSource() {
      DriverManagerDataSource ds = new DriverManagerDataSource();
      ds.setDriverClassName("org.h2.Driver");
      ds.setUrl("jdbc:h2:mem:dietmanager_unit_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;IGNORECASE=TRUE");
      ds.setUsername("SA");
      ds.setPassword("");
      return ds;
  }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
        final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(createLogEventDataSource());
        return transactionManager;
    }
    
    @Bean
    public UserAccountRepository userAccountRepository() {
    	return new UserAccountRepositoryImpl(new JdbcTemplate(createDietManagerDataSource()));
    }
    
    private DatabasePopulator createDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(true);
        databasePopulator.addScript(new ClassPathResource("schema.sql"));
        databasePopulator.addScript(new ClassPathResource("data.sql"));
        return databasePopulator;
    }
}
