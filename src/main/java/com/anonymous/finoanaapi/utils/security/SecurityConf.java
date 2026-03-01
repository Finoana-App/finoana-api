package com.anonymous.finoanaapi.utils.security;

import static com.anonymous.finoanaapi.models.enums.UserRole.MODERATOR;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.anonymous.finoanaapi.controllers.exceptions.ForbiddenException;
import com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConf {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  private final FirebaseFilter firebaseFilter;
  private final HandlerExceptionResolver exceptionResolver;

  SecurityConf(
      FirebaseFilter firebaseFilter,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
    this.firebaseFilter = firebaseFilter;
    this.exceptionResolver = exceptionResolver;
  }

  @Bean
  public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
    // formatter::off
    http.authorizeHttpRequests(
        request ->
            request
                // Health check
                .requestMatchers(GET, "/health/ping")
                .anonymous()
                .requestMatchers(GET, "/health/secured/ping")
                .authenticated()
                // Current user
                .requestMatchers(GET, "/users/me")
                .authenticated()
                .requestMatchers(PUT, "/users/me")
                .authenticated()
                .requestMatchers(DELETE, "/users/me")
                .authenticated()
                // Permission modification
                .requestMatchers(POST, "/users/*/ban")
                .hasRole(MODERATOR.getRole())
                .requestMatchers(POST, "/users/*/unban")
                .hasRole(MODERATOR.getRole())
                .requestMatchers(PUT, "/users/*/role")
                .hasRole(MODERATOR.getRole())
                // Basic user manipulation
                .requestMatchers(GET, "/users")
                .hasRole(MODERATOR.getRole())
                .requestMatchers(GET, "/users/search")
                .authenticated()
                .requestMatchers(POST, "/users/register")
                .permitAll()
                .requestMatchers(GET, "/users/*")
                .authenticated()
                // Other
                .anyRequest()
                .denyAll());

    // TODO: must be enable and configured if possible
    http.cors(AbstractHttpConfigurer::disable);
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);

    // TODO: don't make not necessary request passthrough this
    http.addFilterBefore(firebaseFilter, UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(
        handler ->
            handler
                .accessDeniedHandler(
                    ((request, response, accessDeniedException) -> {
                      exceptionResolver.resolveException(
                          request, response, null, new ForbiddenException("Forbidden"));
                    }))
                .authenticationEntryPoint(
                    ((request, response, accessDeniedException) -> {
                      exceptionResolver.resolveException(
                          request, response, null, new ForbiddenException("Forbidden"));
                    })));

    return http.build();
  }
}
