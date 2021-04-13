package kr.joljak.api.teams.request;

import javax.validation.constraints.NotNull;
import kr.joljak.domain.team.dto.UpdateTeam;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTeamRequest {
  
  @NotNull
  private String teamName;
  @NotNull
  private ProjectCategory category;
  @NotNull
  private String content;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private String deleteFileName;
  @Setter
  private MultipartFile file;
  
  @Builder
  public UpdateTeamRequest(
    String teamName, ProjectCategory category, String content,
    String mediaArtMember, String developerMember,
    String designerMember, String plannerMember,
    MultipartFile file, String deleteFileName
  ){
    this.teamName = teamName;
    this.category = category;
    this.content = content;
    this.mediaArtMember = mediaArtMember;
    this.developerMember = developerMember;
    this.designerMember = designerMember;
    this.plannerMember = plannerMember;
    this.file = file;
    this.deleteFileName = deleteFileName;
  }
  
  public static UpdateTeam toUpdateTeam(UpdateTeamRequest updateTeamRequest){
    return UpdateTeam.builder()
      .teamName(updateTeamRequest.getTeamName())
      .category(updateTeamRequest.getCategory())
      .content(updateTeamRequest.getContent())
      .mediaArtMember(updateTeamRequest.getMediaArtMember())
      .developerMember(updateTeamRequest.getDeveloperMember())
      .designerMember(updateTeamRequest.getDesignerMember())
      .plannerMember(updateTeamRequest.getPlannerMember())
      .file(updateTeamRequest.getFile())
      .deleteFileName(updateTeamRequest.getDeleteFileName())
      .build();
  }
  
}
