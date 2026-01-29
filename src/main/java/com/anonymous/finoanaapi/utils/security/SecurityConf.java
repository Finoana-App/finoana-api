package com.anonymous.finoanaapi.utils.security;

import com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConf {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  private final FirebaseFilter firebaseFilter;

  SecurityConf(FirebaseFilter firebaseFilter) {
    this.firebaseFilter = firebaseFilter;
  }

  @Bean
  public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
    // formatter::off
    http.authorizeHttpRequests(
        request ->
            request
                .requestMatchers("/health/ping")
                .anonymous()
                .requestMatchers("/health/secured/ping")
                .authenticated()
                .anyRequest()
                .denyAll());

    // TODO: must be enable and configured if possible
    http.cors(AbstractHttpConfigurer::disable);
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);

    // TODO: don't make not necessary request passthrough this
    http.addFilterBefore(firebaseFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
