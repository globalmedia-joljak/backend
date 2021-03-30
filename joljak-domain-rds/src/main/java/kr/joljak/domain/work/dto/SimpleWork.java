package kr.joljak.domain.work.dto;

import java.util.List;
import kr.joljak.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleWork {

  private String workName;
  private String teamName;
  private List<String> teamMember;
  private String content;
  private String teamVideoUrl;
  private User user;
  private List<String> deleteFileName;

  @Builder
  public SimpleWork(
    String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl, User user, List<String> deleteFileName
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.user = user;
    this.deleteFileName = deleteFileName;
  }

  public void setUser(User user){
    this.user = user;
  }
}
