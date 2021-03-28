package kr.joljak.domain.work.dto;

import java.util.List;
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

  @Builder
  public SimpleWork(
    String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
  }

}
