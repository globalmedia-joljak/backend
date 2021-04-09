package kr.joljak.domain.work.entity;

public enum ProjectCategory {
  WEB("WEB"),
  FILM("FILM"),
  ANIMATION("ANIMATION"),
  MEDIA_ART("MEDIA_ART");
  
  private String name;
  
  ProjectCategory(String name){this.name = name;}
  
  public ProjectCategory getKey(String name){return ProjectCategory.valueOf(name);}
  public String getType(){return name;}
  
}
