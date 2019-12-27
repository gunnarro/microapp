package com.gunnarro.followup.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

	private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);

	// If the same property is defined as a system property and in the properties
	// file, then the system property would be applied.
	@Value("${jdbc.url}")
	private String jdbcUrl;

	@Value("${jdbc.user}")
	private String jdbcUser;

	@Value("${jdbc.pwd}")
	private String jdbcPwd;

	@Value("${jdbc.driverClassName}")
	private String jdbcDriverClassName;

	// bean is singleton as default
	@Primary
	@Bean
	public DataSource dietManagerDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(jdbcDriverClassName);
		ds.setUrl(jdbcUrl);
		ds.setUsername(jdbcUser);
		ds.setPassword(jdbcPwd);
		Properties p = new Properties();
		p.put("useSSL", "false");
		ds.setConnectionProperties(p);
		LOG.info("jdbc url : {}", jdbcUrl);
		LOG.info("jdbc user: {}", jdbcUser);
		LOG.info("jdbc pwd: {}", jdbcPwd);
		LOG.info(System.getProperty("spring.config.location"));
		// runUpdateDBScript(ds);
		return ds;
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

	@Bean
	public UserAccountRepository userAccountRepository() {
		return new UserAccountRepositoryImpl(new JdbcTemplate(dietManagerDataSource()));
	}
}
