package com.mno.business.config;

import com.mno.business.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class Configer {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(
                AbstractHttpConfigurer::disable
        );
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(
                        auth -> {
                            try {
                                auth
                                        .requestMatchers(
                                                "/api/check/gmailcheck",
                                                "/api/product/**",
                                                "/api/auth/**",
                                                "/api/image/**",
                                                "/api/otp/**"
                                        ).permitAll()
                                        .requestMatchers("/api/user/delete/**").hasAuthority(Role.ADMIN.name())
                                        .requestMatchers("/**").fullyAuthenticated()
                                        .anyRequest().authenticated()
                                        .and()
                                        .authenticationProvider(authenticationProvider)
                                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                        .logout()
                                        .logoutUrl("/api/logout")
                                        .addLogoutHandler(logoutHandler)
                                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());


                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .httpBasic(withDefaults());

        return http.build();
    }


    @Bean
    public WebMvcConfigurer alloweconfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200/", "http://localhost:3000/")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };

    }


}
