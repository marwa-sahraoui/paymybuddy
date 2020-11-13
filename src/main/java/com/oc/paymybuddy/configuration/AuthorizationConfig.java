package com.oc.paymybuddy.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
public class AuthorizationConfig extends WebSecurityConfigurerAdapter {
/*permet la configuration d'autorisation ainsi de définir les accés aux services REST de l'application
 par quel utilisateur ainsi que son rôle*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http
                .csrf().disable().exceptionHandling()
                .and()
                .headers().frameOptions().deny()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/testCreate").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic();

        // @formatter:on


    }
}
