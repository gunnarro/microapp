package com.gunnarro.dietmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
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

//    @Value("${jdbc.url}")
//    private String jdbcUrl;
//
//    @Value("${jdbc.user}")
//    private String jdbcUser;
//
//    @Value("${jdbc.pwd}")
//    private String jdbcPwd;
//
//    @Value("${jdbc.driverClassName}")
//	private String jdbcDriverClassName;

    /**
	 * The gcp datasource is auto configured and autowired as jdbc template
	 */
    @Autowired
//    @Qualifier("dietManagerJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
//    @Autowired
//    private DataSource gcpDataSource;
    
 // bean is singleton as default
// 	@Bean(name = "dietManagerDataSource")
// 	@Primary
// 	public DataSource dietManagerDataSource() {
// 		DriverManagerDataSource ds = new DriverManagerDataSource();
// 		ds.setDriverClassName(jdbcDriverClassName);
// 		ds.setUrl(jdbcUrl);
// 		ds.setUsername(jdbcUser);
// 		ds.setPassword(jdbcPwd);
// 		Properties p = new Properties();
// 		p.put("useSSL", "false");
// 		p.put("useUnicode", "true");
// 		p.put("passwordCharacterEncoding", "UTF-8");
// 		p.put("characterEncoding", "UTF-8");
// 		ds.setConnectionProperties(p);
// 		return ds;
// 		return gcpDataSource;
// 	}
    
    
    // bean is singleton as default
//    @Bean(name = "dietManagerDataSource")
//    @Primary
//    public DataSource dietManagerDataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
//        ds.setUrl(jdbcUrl);
//        ds.setUsername(jdbcUser);
//        ds.setPassword(jdbcPwd);
//        Properties p = new Properties();
//        p.put("useSSL", "false");
//        ds.setConnectionProperties(p);
//        runUpdateDBScript(ds);
//        return ds;
//    }

    private void runUpdateDBScript(DataSource ds) {
        try {
            DatabasePopulatorUtils.execute(createDatabasePopulator(), ds);
        } catch (Exception e) {
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

//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dietManagerDataSource());
//    }

    @Bean
    public UserAccountRepository userAccountRepository() {
//        return new UserAccountRepositoryImpl(new JdbcTemplate(dietManagerDataSource()));
    	return new UserAccountRepositoryImpl(jdbcTemplate);
    }
}
