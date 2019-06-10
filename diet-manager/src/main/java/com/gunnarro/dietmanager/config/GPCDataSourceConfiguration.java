package com.gunnarro.dietmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * ref:
 * https://egkatzioura.com/2016/04/29/spring-boot-and-database-initialization/
 * 
 * @author admin
 *
 */
//@Configuration
//@EnableTransactionManagement
public class GPCDataSourceConfiguration {


	// read system variables
    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.pwd}")
    private String jdbcPwd;

    @Value("${jdbc.driverClassName}")
	private String jdbcDriverClassName;
    
    /**
	 * The gcp datasource is auto configured and autowired as jdbc template
	 */
    @Autowired
	private JdbcTemplate jdbcTemplate;
	

//    @Bean
//    public UserAccountRepository userAccountRepository() {
//    	return new UserAccountRepositoryImpl(jdbcTemplate);
//    }
}
