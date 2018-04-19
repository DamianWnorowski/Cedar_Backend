package main.java.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public FilterRegistrationBean loginFilterRegistrationBean() {
        Set<String> urlPatterns = new HashSet<>();
        urlPatterns.add("/movie/*");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("login");
        registrationBean.setUrlPatterns(urlPatterns);
        LoginFilter loginFilter = new LoginFilter();
        registrationBean.setFilter(loginFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
