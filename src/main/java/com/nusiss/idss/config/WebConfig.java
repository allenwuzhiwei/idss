package com.nusiss.idss.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
                .allowedOrigins("http://localhost:3000") // Allow requests from React frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow only specific HTTP methods
                .allowedHeaders("*") // Allow any header
                .allowCredentials(true); // Allow cookies and authentication
    }
}
