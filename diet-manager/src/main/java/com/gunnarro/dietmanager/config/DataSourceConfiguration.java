package com.gunnarro.dietmanager.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.gunnarro.dietmanager.repository.impl.DietManagerRepositoryImpl;
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

	// If the same property is defined as a system property and in the properties file, then the system property would be applied.
    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.pwd}")
    private String jdbcPwd;

    @Value("${jdbc.driverClassName}")
	private String jdbcDriverClassName;

//    @Autowired
//    @Qualifier("dietManagerJdbcTemplate")
//	private JdbcTemplate jdbcTemplate;
	
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
        LOG.info("jdbc url : " + jdbcUrl);
        LOG.info("jdbc user: " + jdbcUser);
        LOG.info(System.getProperty("spring.config.location"));
//        runUpdateDBScript(ds);
        return ds;
    }
    
//    @Bean
//    @Qualifier("dietManagerDataSource")
//	public JdbcTemplate dietManagerJdbcTemplate(DataSource dataSource) {
//		return new JdbcTemplate(dataSource);
//	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource datasource) {
		return new DataSourceTransactionManager(datasource);
	}

    
//  private void runUpdateDBScript(DataSource ds) {
//  try {
//      DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
//  } catch (Exception e) {
//      throw new RuntimeException("Error init datasource: " + e.getMessage());
//  }
//}
//
//private ResourceDatabasePopulator createDatabasePopulator() {
//  ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//  databasePopulator.setContinueOnError(true);
//  databasePopulator.addScript(new ClassPathResource("update.sql"));
//  return databasePopulator;
//}

	
	
    /**
     * same as dietmanager data source
     * 
     * @return
     */
//    @Bean(name = "logEventDataSource")
//    // @Primary
//    public DataSource logEventDataSource() {
//        return dietManagerDataSource();
//    }

//    @Bean(name = "activityDataSource")
//    public DataSource activityDataSource() {
//    	DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
//        ds.setUrl("dbc:mysql://google/petclinic?cloudSqlInstance=INSTANCE_CONNECTION_NAME&socketFactory=com.google.cloud.sql.mysql.SocketFactory");
//        ds.setUsername("root");
//        ds.setPassword("ABcd2o1o");
////        Properties p = new Properties();
////        p.put("useSSL", "false");
////        ds.setConnectionProperties(p);
//        runUpdateDBScript(ds);
//        return ds;
//    }

    @Bean
    public UserAccountRepository userAccountRepository() {
        return new UserAccountRepositoryImpl(new JdbcTemplate(dietManagerDataSource()));
    }
}
