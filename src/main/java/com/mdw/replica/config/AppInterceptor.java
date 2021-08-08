package com.mdw.replica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.mdw.replica.app.InterceptorConfig;

@Configuration
public class AppInterceptor extends WebMvcConfigurationSupport {

	@Autowired
	InterceptorConfig appInterceptor;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		super.addInterceptors(registry);
		registry.addInterceptor(appInterceptor);
	}

	
	
}
