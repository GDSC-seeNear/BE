package seeNear.seeNear_BE;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seeNear.seeNear_BE.exception.GlobalExceptionHandlerFilter;

@Configuration
public class SpringConfig {
    @Bean
    public FilterRegistrationBean<GlobalExceptionHandlerFilter> GlobalExceptionHandlerFilter(){
        FilterRegistrationBean<GlobalExceptionHandlerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GlobalExceptionHandlerFilter());
        registrationBean.addUrlPatterns("/*");
        //registrationBean.setOrder(1);
        registrationBean.setName("first-filter");
        return registrationBean;
    }

}
