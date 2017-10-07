package com.gunnarro.dietmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
//@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	 public WebMvcConfig() {
		 super();
	 }
	 
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/dietmanager")
//				.allowedOrigins("http://localhost:8080", "https://localhost:8080")
//				.allowedMethods("POST", "GET", "PUT", "DELETE")
//				.allowedHeaders("Content-Type")
//				.exposedHeaders("header-1", "header-2")
//				.allowCredentials(false)
//				.maxAge(6000);
//	}
}
