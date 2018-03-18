package com.gunnarro.dietmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gunnarro.dietmanager.handler.AppSuccessHandler;
import com.gunnarro.dietmanager.handler.CustomAccessDeniedHandler;

/**
 * 
 * @author admin
 *
 */
@Configuration
// @EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppSuccessHandler successHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * default constructor
     */
    public SecurityConfiguration() {
        super();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/", "/login*", "/about", "/releasenotes", "/webjars/**", "/css/**", "/js/**", "/images/**", "/error/**")
                .permitAll().antMatchers("/admin/**").hasAnyRole("ADMIN").antMatchers("/rest/**").hasAnyRole("USER").antMatchers("/**").hasAnyRole("USER")
                .anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login").permitAll().and().logout().permitAll().and()
                .exceptionHandling().accessDeniedHandler(this.accessDeniedHandler);
    }

}
