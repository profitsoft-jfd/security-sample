package dev.profitsoft.jfd.securitysample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  @Profile("!basic")
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authRequests -> authRequests
            .requestMatchers("/*.jpg").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout").permitAll()
        )
        .build();
  }

  @Bean
  @Profile("basic")
  public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authRequests -> authRequests
            .requestMatchers("/api/**").hasAuthority("PRIV_API_ACCESS")
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .build();
  }

}
