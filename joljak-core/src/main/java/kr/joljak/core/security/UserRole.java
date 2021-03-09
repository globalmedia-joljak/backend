package kr.joljak.core.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum UserRole {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER");

  private String roleName;

  UserRole(String roleName) {
    this.roleName = roleName;
  }

  public String getKey() {
    return name();
  }

  public String getRoleName() {
    return roleName;
  }
}
