package com.ddogdog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ddogdog.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private JwtInterceptor jwtInterceptor;
	
	public WebConfig(JwtInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
		.excludePathPatterns("/api/user/**", "/swagger-ui/**", "/v3/api-docs/**");
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "/petPhoto/**" URL을 "static/petPhoto/" 디렉토리와 매핑
        registry.addResourceHandler("/petPhoto/**")
                .addResourceLocations("file:src/main/resources/static/petPhoto/");
    }
}
