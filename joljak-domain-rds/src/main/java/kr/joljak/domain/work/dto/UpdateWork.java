package kr.joljak.domain.work.dto;

import java.util.List;
import kr.joljak.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateWork {

  private String workName;
  private String teamName;
  private List<String> teamMember;
  private String content;
  private String teamVideoUrl;
  private User user;
  private List<String> deleteFileName;

  @Builder
  public UpdateWork(
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
}
