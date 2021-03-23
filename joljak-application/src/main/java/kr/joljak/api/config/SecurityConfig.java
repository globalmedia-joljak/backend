package kr.joljak.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.api.filter.JwtAccessDeniedHandler;
import kr.joljak.api.filter.JwtAuthenticationEntryPoint;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtAuthenticationEntryPoint authenticationErrorHandler;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  @Value("${app.web-navigator.origin}") private String webNavigatorOrigin;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .cors().and().csrf().disable()
      .httpBasic().disable()
      .formLogin().disable()
      .logout().disable()

      .exceptionHandling()
      .authenticationEntryPoint(authenticationErrorHandler)
      .accessDeniedHandler(jwtAccessDeniedHandler)

      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests()
      .mvcMatchers(
        "/", "/csrf", "/v2/api-docs", "/swagger-resources/**",
        "/swagger-ui/**", "/webjars/**", "/swagger/**", "/swagger-ui.html", "/swagger-ui.html/**",
        "/configuration/**"
      ).permitAll()
      .antMatchers(
        "/api/v1/auth/signup", "/api/v1/auth/signin", "/api/v1/auth/reissue/accesstoken"
          , "/api/v1/notices"
      ).permitAll()
      .antMatchers(HttpMethod.GET, "/api/v1/profiles/**").permitAll()
      .antMatchers(
        "/api/v1/invites/**"
      ).hasRole(UserRole.ADMIN.getKey())
      .anyRequest().authenticated()

      .and()
      .apply(securityConfigurerAdapter());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers(
        "/", "/h2-console/**"
      );
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    List<String> origins = new ArrayList<>(
      Arrays.asList("http://localhost:3000", "http://127.0.0.1:3000", webNavigatorOrigin)
    );
    configuration.setAllowedOrigins(origins);
    configuration.addAllowedOrigin("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  private JwtConfig securityConfigurerAdapter() {
    return new JwtConfig(jwtTokenProvider);
  }
}
