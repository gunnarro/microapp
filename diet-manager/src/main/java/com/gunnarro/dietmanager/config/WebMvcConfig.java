package com.gunnarro.dietmanager.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * <init-param> <param-name>cors.allowed.methods</param-name>
     * <param-value>GET,POST,HEAD,OPTIONS,PUT</param-value> </init-param>
     * 
     * @return
     */
    // @Bean
    // public EmbeddedServletContainerFactory servletContainer() {
    // TomcatEmbeddedServletContainerFactory tomcat = new
    // TomcatEmbeddedServletContainerFactory();
    // return tomcat;
    // }
    //

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "HEAD", "PUT", "OPTIONS");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("no"));
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

    // @Bean
    // @Description("Thymeleaf template resolver serving HTML 5")
    // public ClassLoaderTemplateResolver templateResolver() {
    // ClassLoaderTemplateResolver templateResolver = new
    // ClassLoaderTemplateResolver();
    // templateResolver.setPrefix("templates/");
    // templateResolver.setCacheable(false);
    // templateResolver.setSuffix(".html");
    // templateResolver.setTemplateMode("HTML5");
    // templateResolver.setCharacterEncoding("UTF-8");
    // return templateResolver;
    // }
    //
    // @Bean
    // @Description("Thymeleaf template engine with Spring integration")
    // public SpringTemplateEngine templateEngine() {
    // SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    // templateEngine.setTemplateResolver(templateResolver());
    // return templateEngine;
    // }
    //
    // @Bean
    // @Description("Thymeleaf view resolver")
    // public ViewResolver viewResolver() {
    // ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    // viewResolver.setTemplateEngine(templateEngine());
    // viewResolver.setCharacterEncoding("UTF-8");
    // return viewResolver;
    // }
}
