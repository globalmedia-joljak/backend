package kr.joljak.api.config;

import kr.joljak.api.filter.JwtAccessDeniedHandler;
import kr.joljak.api.filter.JwtAuthenticationEntryPoint;
import kr.joljak.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtAuthenticationEntryPoint authenticationErrorHandler;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf().disable()
      .httpBasic().disable()
      .formLogin().disable()
      .csrf().disable()
      .logout().disable()

      .exceptionHandling()
      .authenticationEntryPoint(authenticationErrorHandler)
      .accessDeniedHandler(jwtAccessDeniedHandler)

      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests()
      .antMatchers(
        HttpMethod.GET, "/", "/csrf", "/v2/api-docs", "/swagger-resources/**",
        "/swagger-ui/**", "/webjars/**", "/swagger/**"
      ).permitAll()
      .antMatchers("/api/v1/users/**").permitAll()
      .anyRequest().authenticated()

      .and()
      .apply(securityConfigurerAdapter());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers(
        "/",
        "/h2-console/**"
      );
  }

  private JwtConfig securityConfigurerAdapter() {
    return new JwtConfig(jwtTokenProvider);
  }
}
