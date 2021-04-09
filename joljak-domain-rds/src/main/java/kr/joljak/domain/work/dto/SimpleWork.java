package kr.joljak.domain.work.dto;

import java.util.List;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleWork {

  private String workName;
  private String teamName;
  private List<String> teamMember;
  private ProjectCategory projectCategory;
  private String year;
  private String content;
  private String teamVideoUrl;
  private User user;

  @Builder
  public SimpleWork(
    String workName, String teamName, List<String> teamMember, String year,
    String content, String teamVideoUrl, User user, ProjectCategory projectCategory
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.projectCategory = projectCategory;
    this.year = year;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.user = user;

  }

  public void setUser(User user){
    this.user = user;
  }
}
