package com.nvans.tyrannophone.config;

import com.nvans.tyrannophone.service.helper.StringToCustomerConverter;
import com.nvans.tyrannophone.service.helper.StringToOptionConverter;
import com.nvans.tyrannophone.service.helper.StringToOptionDtoConverter;
import com.nvans.tyrannophone.service.helper.StringToPlanConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.concurrent.TimeUnit;

/**
 * Spring MVC configuration
 *
 */
@Configuration
@EnableWebMvc
@EnableAsync
@EnableAspectJAutoProxy
@ComponentScan({ "com.nvans.tyrannophone" })
@Import({ WebSecurityConfig.class })
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private StringToOptionConverter stringToOptionConverter;

    @Autowired
    private StringToCustomerConverter stringToCustomerConverter;

    @Autowired
    private StringToPlanConverter stringToPlanConverter;

    @Autowired
    private StringToOptionDtoConverter stringToOptionDtoConverter;



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

        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(stringToOptionConverter);
        registry.addConverter(stringToCustomerConverter);
        registry.addConverter(stringToPlanConverter);
        registry.addConverter(stringToOptionDtoConverter);

    }


}
