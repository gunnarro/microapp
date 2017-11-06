package com.gunnarro.dietmanager.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.gunnarro.dietmanager.handler.CustomAccessDeniedHandler;
import com.gunnarro.dietmanager.service.impl.LocalUserDetailsServiceImpl;

@Configuration
public class BeanConfiguration {

	@Bean
	public SimpleUrlAuthenticationFailureHandler failureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/access-denied");
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public StandardPBEStringEncryptor pwdEncoder() {
		StandardPBEStringEncryptor crypt = new StandardPBEStringEncryptor();
		crypt.setPassword("duMMY-enCrypT-pwd-x3");
		crypt.setAlgorithm("PBEWithMD5AndTripleDES");
		return crypt;
	}

	@Bean
	@Qualifier(value = "pwdEncoder")
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(13);
	}

	@Bean
	public CustomAccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new LocalUserDetailsServiceImpl();
	}
}
