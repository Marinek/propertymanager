package de.marinek.propertymanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private ThymeleafLayoutInterceptor thymleafInterceptor;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/page-login.html");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(thymleafInterceptor);
	}

}