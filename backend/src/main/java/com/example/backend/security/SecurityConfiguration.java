package com.example.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    // configurazione su come avere uno UserDetailsService
    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // configurazione su come avere un PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // configurazione dell'AuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // creo un DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli assegno lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        // gli assegno il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // SecurityFilterChain che fa da "dogana"
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // le rotte /categories, /borrowings e /users solo per ADMIN
        http
                .authorizeHttpRequests()
                .requestMatchers("/categories").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")
                .requestMatchers("/photos", "/photos/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/**").permitAll()
                .and().formLogin().
                defaultSuccessUrl("/photos", true)
                .and().logout();
        http.csrf().disable();
        return http.build();
    }

}
