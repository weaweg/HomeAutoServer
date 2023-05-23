package bbudzowski.homeautoserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        WebExpressionAuthorizationManager localAccess = new WebExpressionAuthorizationManager(
                "hasIpAddress('192.168.0.0/16') or hasIpAddress('::1')");

        http.cors().and().csrf().disable().authorizeHttpRequests()
                .requestMatchers("*/local/**").access(localAccess)
                .requestMatchers("/**").authenticated()
                .anyRequest().denyAll()
                .and().httpBasic();
        return http.build();
    }
}

