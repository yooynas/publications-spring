package com.carlospaelinck.config

import com.carlospaelinck.security.PublicationsUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import javax.inject.Inject

/**
 * Created by carlos13 on 1/25/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    def PublicationsUserDetailsService publicationsUserDetailsService

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/users/login")
                .permitAll()
            .antMatchers(HttpMethod.POST, "/users")
                .permitAll()
            .anyRequest()
                .authenticated()
    }

    @Inject
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(publicationsUserDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}