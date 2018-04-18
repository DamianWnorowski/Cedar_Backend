package main.java.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }   
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
     // LimitLoginAuthenticationProvider is my own class which extends DaoAuthenticationProvider 
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: Enable csrf again and put in the csrf token in the form
        http
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
            .httpBasic()
                .and()
            .cors()
                .and()
            .authorizeRequests()
                .antMatchers("/profile")
                .authenticated()
//                .and()
//            .authorizeRequests()
//                .anyRequest().permitAll()
            ;
    }
    
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//            .ignoring()
//                .antMatchers("/register")
//                .antMatchers("/login");
//}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authenticationProvider);
    }
}