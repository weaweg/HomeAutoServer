package bbudzowski.homeautoserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        WebExpressionAuthorizationManager localAccess = new WebExpressionAuthorizationManager(
                "hasIpAddress('192.168.0.0/16') or hasIpAddress('::1')");
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("*/local/**").access(localAccess)
                .requestMatchers("/devices/**").authenticated()
                .requestMatchers("/sensors/**").authenticated()
                .requestMatchers("/measurements/**").authenticated()
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().denyAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
