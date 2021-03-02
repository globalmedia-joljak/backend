package kr.joljak.user.entity;

public enum UserProjectRole {
  DEVELOPER("개발자"),
  DESIGNER("디자이너"),
  MEDIA_ART("미디어아트"),
  PLANNER("기획자");

  private String name;

  UserProjectRole(String name) {
    this.name = name;
  }

  public String getKey() {
    return name();
  }

  public String getRoleName() {
    return name;
  }
}
