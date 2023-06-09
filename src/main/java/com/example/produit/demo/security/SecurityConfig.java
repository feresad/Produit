package com.example.produit.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        PasswordEncoder passwordEncoder = passwordEncoder ();
        auth.inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder.encode("123")).roles("ADMIN");
        auth.inMemoryAuthentication().withUser("Najla")
                .password(passwordEncoder.encode("123")).roles("AGENT","USER");
        auth.inMemoryAuthentication().withUser("user1")
                .password(passwordEncoder.encode("123")).roles("USER");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        http.authorizeRequests().antMatchers("/showCreate","/saveProduit","/ok","/saveCat","/updateProduit").hasAnyRole("ADMIN"
                ,"AGENT");
        http.authorizeRequests().antMatchers("/ListeProduits","/ListeCategories")
                .hasAnyRole("ADMIN","AGENT","USER");
        http.authorizeRequests()
                .antMatchers("/supprimerProduit","/modifierProduit","/updateProduit","/modifierCategorie","/supprimerCategories","/editCategorie")
                .hasAnyRole("ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
        http.exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}