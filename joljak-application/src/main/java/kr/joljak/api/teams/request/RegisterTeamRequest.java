package kr.joljak.api.teams.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.team.dto.SimpleTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class RegisterTeamRequest {

  @NotNull
  private String teamName;
  @NotNull
  private String content;
  @NotNull
  private String category;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  @Setter
  private List<MultipartFile> images;

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
      .images(registerTeamRequest.getImages())
      .build();
  }

}
