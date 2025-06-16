package com.examples.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**")
        .allowedOriginPatterns("http://localhost:8100", "http://192.168.1.137:8100")
        .allowedMethods("*")
        .allowedHeaders("*")
        .allowCredentials(true);
    }
}

