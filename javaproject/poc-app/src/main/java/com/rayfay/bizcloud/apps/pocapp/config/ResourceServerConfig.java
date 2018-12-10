package com.rayfay.bizcloud.apps.pocapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by shenfu on 2017/5/4.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatcher(new OAuth2TokenRequestMatcher()).authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().antMatchers("/v2/**", "/swagger**", "/images/**", "/configuration/**").permitAll();
    }
}
