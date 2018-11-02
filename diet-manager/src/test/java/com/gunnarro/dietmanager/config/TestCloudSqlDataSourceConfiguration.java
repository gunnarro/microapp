package com.gunnarro.dietmanager.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.gunnarro.dietmanager.repository.ActivityRepository;
import com.gunnarro.dietmanager.repository.impl.ActivityRepositoryImpl;

/**
 * ref:
 * https://egkatzioura.com/2016/04/29/spring-boot-and-database-initialization/
 * 
 * @author admin
 *
 */
@Configuration
// @EnableTransactionManagement
public class TestCloudSqlDataSourceConfiguration {

	// @Autowired
	// private DataSource cloudSqlDataSource;

	@Value("${cloudsql.jdbc.url}")
	private String jdbcUrl;

	@Value("${cloudsql.jdbc.username}")
	private String jdbcUser;

	@Value("${cloudsql.jdbc.pwd}")
	private String jdbcPwd;

	@Value("${cloudsql.jdbc.driverClassName}")
	private String jdbcDriverClassName;

	// bean is singleton as default
	@Bean(name = "activityDataSource")
	@Primary
	public DataSource cloudSqlDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(jdbcDriverClassName);
		ds.setUrl(jdbcUrl);
		ds.setUsername("root");
		ds.setPassword("ABcd2o1o");
		Properties p = new Properties();
		p.put("useSSL", "false");
		p.put("useUnicode", "true");
		p.put("passwordCharacterEncoding", "UTF-8");
		p.put("characterEncoding", "UTF-8");
		ds.setConnectionProperties(p);
		return ds;
	}

	// @Bean
	// public DataSourceTransactionManager transactionManager(DataSource datasource)
	// {
	// return new DataSourceTransactionManager(datasource);
	// }

	@Bean
	public ActivityRepository activityRepository() {
		return new ActivityRepositoryImpl(new JdbcTemplate(cloudSqlDataSource()));
	}
}
