package kr.joljak.api.work.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.work.dto.SimpleWork;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterWorkRequest {

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
  public RegisterWorkRequest(String workName, String teamName,
    List<String> teamMember, String content, String teamVideoUrl
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
  }

  public static SimpleWork toDomainWorkRequest(RegisterWorkRequest registerWorkRequest) {
    return SimpleWork.builder()
      .workName(registerWorkRequest.getWorkName())
      .teamName(registerWorkRequest.getTeamName())
      .teamMember(registerWorkRequest.getTeamMember())
      .content(registerWorkRequest.getContent())
      .teamVideoUrl(registerWorkRequest.getTeamVideoUrl())
      .build();
  }

}
