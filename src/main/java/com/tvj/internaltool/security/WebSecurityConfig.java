package com.tvj.internaltool.security;

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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    // Must declare Filter in WebSecurityConfig to join FilterChain
    @Bean
    public JwtRequestFilter jwtAuthenticationTokenFilter() {
        return new JwtRequestFilter();
    }

    // Must declare Filter in WebSecurityConfig to join FilterChain
    @Bean
    public UserRoleFilter userRoleFilter() {
        return new UserRoleFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Ignore CSRF protection
        httpSecurity.csrf().ignoringAntMatchers("/**");

        // Make sure we use stateless session; session won't be used to store user's state.
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Permit specific requests
        httpSecurity.authorizeRequests().antMatchers("/user/login").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/user/password-recover-send-request").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/user/password-recover-confirm-token").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/user/password-recover-update-password").permitAll();

        // Authenticate the rest of requests
        httpSecurity.authorizeRequests().anyRequest().authenticated();

        // Add a filter to validate the tokens with every request,
        // JwtRequestFilter is executed before UsernamePasswordAuthenticationFilter
        // Must implement at least one filter to activate filter chain
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // Check if authorize fail
        httpSecurity.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
}
