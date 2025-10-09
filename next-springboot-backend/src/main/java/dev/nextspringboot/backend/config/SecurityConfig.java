package dev.nextspringboot.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Value("${app.security.disabled:true}")
  private boolean securityDisabled;

    @Value("${app.security.mode:basic}") // basic or jwt
  private String mode;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    if (securityDisabled) {
      return http.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
          .build();
    }

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/health", "/actuator/health", "/api/auth/register", "/api/auth/login", "/api/auth/refresh").permitAll()
            .requestMatchers("/api/posts/**").hasRole("post_writer")
            .requestMatchers("api/auth/logout", "/api/auth/change-password").authenticated()
            .anyRequest().authenticated())
        .sessionManagement(sm -> sm.sessionCreationPolicy(
            org.springframework.security.config.http.SessionCreationPolicy.STATELESS));

    if ("jwt".equalsIgnoreCase(mode)) {
      // ← 将来 Keycloak に戻す時はこちらを有効化
      http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
    } else {
      // 開発用 Basic 認証
      http.httpBasic(Customizer.withDefaults());
    }
    return http.build();
  }

  // 開発用：メモリ内ユーザー
  @Bean
  UserDetailsService userDetailsService(PasswordEncoder encoder) {
    var user = User.withUsername("alice")
        .password(encoder.encode("pass"))
        .roles("post_writer") // → hasRole("post_writer") と対応
        .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
