package com.oc.paymybuddy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
/*permet la configuration d'authentification aussi à travers les requêtes permet d'identifier le user pour notre cas
  email ainsi que son role*/
@Configuration
public class AuthenticationConfig {

    @Autowired
    DataSource datasource;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(datasource)
                .usersByUsernameQuery("select email,password,enabled from User where email = ?")
                .authoritiesByUsernameQuery("select email,role from User where email = ?");
    }

    //permet de définir le composant qui permet le cryptage des MDP
    @Bean
    public PasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder();
    }
}
