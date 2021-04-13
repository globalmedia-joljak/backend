package kr.joljak.api.teams.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTeamRequest {

  @NotNull
  private String teamName;
  @NotNull
  private String content;
  @NotNull
  private ProjectCategory category;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  @Setter
  private MultipartFile file;

  public static SimpleTeam toRegisterTeam(
    RegisterTeamRequest registerTeamRequest
  ) {
    return SimpleTeam.builder()
      .teamName(registerTeamRequest.getTeamName())
      .content(registerTeamRequest.getContent())
      .category(registerTeamRequest.getCategory())
      .mediaArtMember(registerTeamRequest.getMediaArtMember())
      .designerMember(registerTeamRequest.getDesignerMember())
      .developerMember(registerTeamRequest.getDeveloperMember())
      .plannerMember(registerTeamRequest.getPlannerMember())
      .file(registerTeamRequest.getFile())
      .build();
  }

}
