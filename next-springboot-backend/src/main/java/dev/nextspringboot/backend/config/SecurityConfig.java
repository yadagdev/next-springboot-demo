package dev.nextspringboot.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Value("${app.security.disabled:true}")
  private boolean securityDisabled;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    if (securityDisabled) {
      // 開発用にセキュリティを無効化
      return http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        .build();
    }

    // 本番用のセキュリティ設定
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/health", "/actuator/health").permitAll()
        .anyRequest().authenticated())
      .oauth2ResourceServer(oauth2 -> oauth2.jwt());
    return http.build();
  }
}
