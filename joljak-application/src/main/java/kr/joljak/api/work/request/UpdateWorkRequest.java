package kr.joljak.api.work.request;

import java.util.List;
import kr.joljak.domain.work.dto.UpdateWork;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateWorkRequest {

  private String workName;
  private String teamName;
  private List<String> teamMember;
  private String content;
  private String year;
  private ProjectCategory projectCategory;
  private String teamVideoUrl;
  private List<String> deleteFileName;

  @Builder
  public UpdateWorkRequest(String workName, String teamName,
    List<String> teamMember, String content, String teamVideoUrl,
    List<String> deleteFileName, ProjectCategory projectCategory, String year
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.projectCategory = projectCategory;
    this.year = year;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.deleteFileName = deleteFileName;
  }

  public static UpdateWork toUpdateWork(UpdateWorkRequest updateWorkRequest) {
    return UpdateWork.builder()
      .workName(updateWorkRequest.getWorkName())
      .teamName(updateWorkRequest.getTeamName())
      .teamMember(updateWorkRequest.getTeamMember())
      .content(updateWorkRequest.getContent())
      .year(updateWorkRequest.getYear())
      .projectCategory(updateWorkRequest.getProjectCategory())
      .deleteFileName(updateWorkRequest.getDeleteFileName())
      .teamVideoUrl(updateWorkRequest.getTeamVideoUrl())
      .build();
  }

}
