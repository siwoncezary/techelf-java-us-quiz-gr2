package pl.us.gr2.quiz.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .realmName("QuizApp")
                .and()
                .csrf()
                .disable()
                .headers()
                .and()
                .authorizeHttpRequests(r -> {
                    r
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/quizzes/**").hasRole("ADMIN")
                            .requestMatchers("/api/v1/quizzes/**").authenticated()
                            .anyRequest().permitAll();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }
}
