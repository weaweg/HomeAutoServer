package bbudzowski.homeautoserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        WebExpressionAuthorizationManager localAccess = new WebExpressionAuthorizationManager(
                "hasIpAddress('192.168.0.0/16') or hasIpAddress('::1')");
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("*/local/**").access(localAccess)
                .requestMatchers("/devices/**").permitAll()
                .requestMatchers("/sensors/**").permitAll()
                .requestMatchers("/data/**").permitAll()
                .anyRequest().denyAll();
        return http.build();
    }
}
