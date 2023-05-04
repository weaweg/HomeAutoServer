package bbudzowski.homeautoserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        WebExpressionAuthorizationManager localAccess = new WebExpressionAuthorizationManager(
                "hasIpAddress('192.168.0.0/16') or hasIpAddress('127.0.0.0/24')");
        http.csrf().disable();
        //http.addFilterBefore(new AuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/devices/local/**").access(localAccess)
                .and().authorizeHttpRequests()
                .requestMatchers("/devices").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    /*@Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.privilegeEvaluator()
        //return (web) -> web.ignoring().requestMatchers("/devices/local/**");
    }*/
}
