package com.gunnarro.dietmanager.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gunnarro.dietmanager.service.impl.LocalUserDetailsServiceImpl;

@Configuration
public class BeanConfiguration {
	
//	@Bean
//	@Qualifier(value="localUserDetailsService")
//	public LocalUserDetailsServiceImpl localUserDetailsServiceImpl() {
//		return new LocalUserDetailsServiceImpl();
//	}

}
