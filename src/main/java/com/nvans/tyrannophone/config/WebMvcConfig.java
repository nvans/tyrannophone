package com.nvans.tyrannophone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC configuration
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "com.nvans.tyrannophone" })
@Import({ WebSecurityConfig.class })
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Helps with mapping view names to JSP files
     *
     * @return jsp view resolver
     */
    @Bean
    public InternalResourceViewResolver jspViewResolver() {

        InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
        jspViewResolver.setPrefix("/WEB-INF/views/");
        jspViewResolver.setSuffix(".jsp");

        return jspViewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
