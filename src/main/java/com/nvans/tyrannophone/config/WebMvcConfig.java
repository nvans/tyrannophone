package com.nvans.tyrannophone.config;

import com.nvans.tyrannophone.service.helper.StringToOptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
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

    @Autowired
    private StringToOptionConverter stringToOptionConverter;

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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToOptionConverter);
    }
}
