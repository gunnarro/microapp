package com.gunnarro.dietmanager.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Tutorial:
 * https://developers.redhat.com/blog/2017/02/23/getting-started-with-openshift-java-s2i
 * 
 * Spring boot example: https://github.com/redhat-helloworld-msa/ola
 * 
 * Openshift reference architecture:
 * https://access.redhat.com/documentation/en-us/reference_architectures/2017/html-single/spring_boot_microservices_on_red_hat_openshift_container_platform_3/
 * 
 * Deploy to openshift:
 * https://blog.codecentric.de/en/2016/03/deploy-spring-boot-applications-openshift/
 *
 * Download openshift tools oc: https://www.openshift.org/download.html
 * 
 * Run: mvn clean compile spring-boot:run
 * 
 * Deploy to openshift: mvn clean package docker:build fabric8:json
 * fabric8:apply
 * 
 * 
 * Run mysql at local host: sudo service mysqld start sudo service mysqld stop
 * 
 * @author admin
 *
 */
@SpringBootApplication
// @ImportResource("spring/spring.xml")
@ComponentScan("com.gunnarro.dietmanager.*")
// @EnableAutoConfiguration(exclude = SocialWebAutoConfiguration.class)
public class DietManagerApp
// extends SpringBootServletInitializer
{

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerApp.class);

    
 // set default properties
     static {
//     System.setProperty("spring.profiles.active", "vktest");
//     System.setProperty("spring.config.location", "config");
     System.setProperty("logging.config", "src/main/resources/log4j2.xml");
     }
    
    // @Override
    // protected SpringApplicationBuilder configure(
    // SpringApplicationBuilder application) {
    // return application.sources(DietManagerApp.class);
    // }

    public static void main(String[] args) {
        LOG.info("Start dietmanager ....");
        SpringApplication.run(DietManagerApp.class, args);
    }
}
