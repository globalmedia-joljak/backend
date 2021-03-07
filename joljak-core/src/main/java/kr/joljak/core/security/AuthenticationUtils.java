package kr.joljak.core.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
  public static String getClassOf() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
