package kr.joljak.api.filter;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.joljak.core.jwt.InvalidTokenException;
import kr.joljak.core.jwt.JwtTokenProvider;
import kr.joljak.core.jwt.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
public class JwtFilter extends GenericFilterBean {

  private static final String AUTHORIZATION = "Authorization";
  private JwtTokenProvider jwtTokenProvider;

  public JwtFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      Optional<String> authorization = getAuthorization(httpServletRequest);

      if (authorization.isPresent()) {
        String token = jwtTokenProvider.getTokenByAuthorization(authorization.get());

        if(token != null && !token.equals("")) {
          Authentication authentication = jwtTokenProvider.getAuthentication(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }

      filterChain.doFilter(servletRequest, servletResponse);
    } catch (InvalidTokenException e) {
      ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    } catch (PermissionException e) {
      ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }

  }

  private Optional<String> getAuthorization(HttpServletRequest request) {
    String authToken = request.getHeader(AUTHORIZATION);
    if (StringUtils.hasText(authToken)) {
      return Optional.of(authToken);
    } else {
      return Optional.empty();
    }
  }
}
