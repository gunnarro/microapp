package com.gunnarro.dietmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	public WebMvcConfig() {
		super();
	}

	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**", "/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources")
          .setCachePeriod(3600)
          .resourceChain(true).addResolver(new PathResourceResolver());
    }

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/dietmanager")
//				.allowedOrigins("http://localhost:8080",
//						"https://localhost:8080")
//				.allowedMethods("POST", "GET", "PUT", "DELETE")
//				.allowedHeaders("Content-Type")
//				.exposedHeaders("header-1", "header-2").allowCredentials(false)
//				.maxAge(6000);
//	}
}
