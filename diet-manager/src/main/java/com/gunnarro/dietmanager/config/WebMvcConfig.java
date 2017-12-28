package com.gunnarro.dietmanager.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

//	@Bean
//	@Description("Thymeleaf template resolver serving HTML 5")
//	public ClassLoaderTemplateResolver templateResolver() {
//		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//		templateResolver.setPrefix("templates/");
//		templateResolver.setCacheable(false);
//		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode("HTML5");
//		templateResolver.setCharacterEncoding("UTF-8");
//		return templateResolver;
//	}
//
//	@Bean
//	@Description("Thymeleaf template engine with Spring integration")
//	public SpringTemplateEngine templateEngine() {
//		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//		templateEngine.setTemplateResolver(templateResolver());
//		return templateEngine;
//	}
//
//	@Bean
//	@Description("Thymeleaf view resolver")
//	public ViewResolver viewResolver() {
//		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//		viewResolver.setTemplateEngine(templateEngine());
//		viewResolver.setCharacterEncoding("UTF-8");
//		return viewResolver;
//	}
}
