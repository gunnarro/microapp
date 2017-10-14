package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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

	// bean is singleton as default
	@Bean(name = "dietManagerDataSource")
	 @Primary
	public DataSource dietManagerDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://127.9.127.2:3306/dietmanager");
		ds.setUsername("web");
		ds.setPassword("wEbt0t3");
		return ds;
	}

	
	/**
	 * same as dietmanager data source
	 * @return
	 */
	@Bean(name = "logEventDataSource")
	//	@Primary
	public DataSource logEventDataSource() {
		return dietManagerDataSource();
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dietManagerDataSource());
		return transactionManager;
	}

	@Bean
	public UserAccountRepository userAccountRepository() {
		return new UserAccountRepositoryImpl(new JdbcTemplate(dietManagerDataSource()));
	}
}
