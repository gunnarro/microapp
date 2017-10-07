package com.gunnarro.dietmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.gunnarro.dietmanager.service.impl.LocalUserDetailsServiceImpl;

/**
 * 
 * @author admin
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * default constructor
	 */
	public SecurityConfiguration() {
		super();
	}

	
	@Bean
	public SimpleUrlAuthenticationFailureHandler failureHandler() {
		return  new SimpleUrlAuthenticationFailureHandler("/access-denied");
	}
	
	@Bean
	@Qualifier(value="pwdEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(13);
    }
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new LocalUserDetailsServiceImpl();
	}
	
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	     auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
	 }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/login", "/access-denied", "/signup", "/register/**").permitAll()
				.antMatchers("/dietmanageer/**").hasRole("USER_ROLE")
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.permitAll()
			.and()
				.logout()
				.permitAll()
			.and()
				.exceptionHandling()
//				.accessDeniedHandler(accessDeniedHandler)
			.and()
				.sessionManagement().maximumSessions(2);

	}
}
