package kr.joljak.api.team.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.team.dto.SimpleTeam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamRequest {

  @NotNull
  private String workName;
  @NotNull
  private String teamName;
  @NotNull
  private List<String> teamMember;
  @NotNull
  private String content;
  private String teamVideoUrl;

  @Builder
  public TeamRequest(String workName, String teamName,
    List<String> teamMember, String content, String teamVideoUrl
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
  }

  public static SimpleTeam toDomainTeamRequest(TeamRequest teamRequest) {
    return SimpleTeam.builder()
      .workName(teamRequest.getWorkName())
      .teamName(teamRequest.getTeamName())
      .teamMember(teamRequest.getTeamMember())
      .content(teamRequest.getContent())
      .teamVideoUrl(teamRequest.getTeamVideoUrl())
      .build();
  }

}
