package kr.joljak.core.jwt;

public enum JwtToken {
  ACCESS_TOKEN("AccessToken"),
  REFRESH_TOKEN("RefreshToken");

  private String name;

  JwtToken(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
