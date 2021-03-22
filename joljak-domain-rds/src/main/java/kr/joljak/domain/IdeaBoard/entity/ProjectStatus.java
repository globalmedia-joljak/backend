package kr.joljak.domain.IdeaBoard.entity;

public enum ProjectStatus {
  COMPLETE("마감"),
  ONGOING("진행중");

  private String name;

  ProjectStatus(String name) {
    this.name = name;
  }

  public String getKey() {
    return name();
  }

  public String getStatus() {
    return name;
  }

}
