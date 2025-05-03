package com.nusiss.idss.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // All endpoints
                .allowedOrigins("http://localhost:3000", "https://localhost:3000", "https://54.198.170.28:81", "https://web-elb-1385983805.us-east-1.elb.amazonaws.com:81", "http://web-elb-1385983805.us-east-1.elb.amazonaws.com:81") // Allowed frontend origin (e.g., React app running on port 3000)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials (cookies)
    }
}