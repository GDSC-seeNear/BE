package seeNear.seeNear_BE;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import seeNear.seeNear_BE.global.common.Filter.RequestWrappingFilter;
import seeNear.seeNear_BE.domain.Medicine.interceptor.IsPossibleMedicineCRUD;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.GuardianRepository;
import seeNear.seeNear_BE.domain.Auth.TokenProvider;
import seeNear.seeNear_BE.domain.Auth.filter.JwtFilter;
import seeNear.seeNear_BE.exception.GlobalExceptionHandlerFilter;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<RequestWrappingFilter> RequestWrappingFilter(){
        FilterRegistrationBean<RequestWrappingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestWrappingFilter());
        registrationBean.addUrlPatterns("/medicine/*");
        registrationBean.setOrder(2);
        registrationBean.setName("second-filter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> JwtFilter(ElderlyRepository elderlyRepository, GuardianRepository guardianRepository,TokenProvider tokenProvider){
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(elderlyRepository,guardianRepository,tokenProvider));
        registrationBean.addUrlPatterns("/guardian/*","/elderly/*","/medicine/*");
        registrationBean.setOrder(1);
        registrationBean.setName("first-filter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<GlobalExceptionHandlerFilter> GlobalExceptionHandlerFilter(){
        FilterRegistrationBean<GlobalExceptionHandlerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GlobalExceptionHandlerFilter());
        registrationBean.addUrlPatterns("/*");
        //registrationBean.setOrder(1);
        registrationBean.setName("first-filter");
        return registrationBean;
    }

    @Bean
    public IsPossibleMedicineCRUD isPossibleMedicineCRUD() {
        return new IsPossibleMedicineCRUD();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(isPossibleMedicineCRUD())
                .addPathPatterns("/medicine/**");

    }
}

