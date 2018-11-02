package com.gunnarro.dietmanager.config;

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
