package com.example.club_project.config;


import com.example.club_project.security.filter.ApiCheckFilter;
import com.example.club_project.security.filter.ApiLoginFilter;
import com.example.club_project.security.handler.LoginFailHandler;
import com.example.club_project.security.handler.LoginSuccessHandler;
import com.example.club_project.security.service.ClubUserDetailsService;
import com.example.club_project.security.util.AuthUtil;
import com.example.club_project.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTUtil jwtUtil() { return new JWTUtil(); }

    @Bean
    public AuthUtil authUtil() {
        return new AuthUtil(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/**", new String[]{"/accounts/signup", "/login"}, jwtUtil());
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter
                ("/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        apiLoginFilter.setAuthenticationFailureHandler(new LoginFailHandler());

        return apiLoginFilter;
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login").permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(userDetailsService);
        http.addFilterBefore(apiCheckFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(),
                UsernamePasswordAuthenticationFilter.class);

    }
}