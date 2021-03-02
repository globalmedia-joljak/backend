package kr.joljak.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {
  @Value("${app.jwt.secret}")
  private String secretKey;
  @Value("${app.jwt.expiration}")
  private Long expirationTime;

  private static final String CLASS_OF = "classOf";
  private static final String ROLES = "roles";

  public AccessToken generateAccessToken(String classOf, List<String> roles) {
    Date now = new Date();
    Date expireDate = new Date(now.getTime() + expirationTime);

    String token = Jwts.builder()
      .setClaims(createDefaultClaims(classOf, roles))
      .setSubject(JwtToken.ACCESS_TOKEN.getName())
      .setIssuedAt(now)
      .setExpiration(expireDate)
      .signWith(getSigningKey())
      .compact();

    return new AccessToken(token, expireDate.getTime());
  }

  public String generateRefreshToken(String classOf, List<String> roles) {

    return Jwts.builder()
      .setHeaderParam("typ", "JWT")
      .setClaims(createDefaultClaims(classOf, roles))
      .setSubject(JwtToken.REFRESH_TOKEN.getName())
      .setIssuedAt(new Date())
      .signWith(getSigningKey())
      .compact();
  }

  private Map<String, Object> createDefaultClaims(String classOf, List<String> roles) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLASS_OF, classOf);
    claims.put(ROLES, roles);

    return claims;
  }

  public Long getClassOfByToken(String token, JwtToken jwtToken){
    Claims claims = decodingToken(token);

    if(!(claims.getSubject().equals(jwtToken.getName())))
      throw new InvalidTokenException("token subject do not match.");

    return Long.parseLong(claims.get(CLASS_OF).toString());
  }

  public Authentication getAuthentication(String token) {
    Claims claims = decodingToken(token);
    List<String> roles = claims.get(ROLES, List.class);

    List<? extends GrantedAuthority> authorities =
      roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    User principal = new User(claims.get(CLASS_OF).toString(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public Claims decodingToken(String token) {
    try {
      return Jwts.parser()
        .setSigningKey(getSigningKey())
        .parseClaimsJws(token)
        .getBody();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new InvalidTokenException("Token decoding failed.");
    }
  }

  public String getTokenByAuthorization(String token) {
    if (token.equals("")) {
      throw new InvalidTokenException("token is empty");
    }
    if (token.contains("Bearer")) {
      token = token.replace("Bearer ", "");
    }
    else {
      log.error("invalid token : " + token);
      throw new InvalidTokenException("Invalid Token");
    }
    return token;
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}