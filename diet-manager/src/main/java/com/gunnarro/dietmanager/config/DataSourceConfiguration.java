package com.gunnarro.dietmanager.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Value( "${jdbc.url}" )
	private String jdbcUrl;
	
	@Value( "${jdbc.user}" )
	private String jdbcUser;
	
	@Value( "${jdbc.pwd}" )
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
