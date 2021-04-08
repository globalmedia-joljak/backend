package kr.joljak.api.teams.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.team.dto.UpdateTeam;
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
  private String category;
  @NotNull
  private String content;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private List<String> deleteFileName;
  @Setter
  private List<MultipartFile> images;
  
  @Builder
  public UpdateTeamRequest(
    String teamName, String category, String content,
    String mediaArtMember, String developerMember,
    String designerMember, String plannerMember,
    List<MultipartFile> images, List<String> deleteFileName
  ){
    this.teamName = teamName;
    this.category = category;
    this.content = content;
    this.mediaArtMember = mediaArtMember;
    this.developerMember = developerMember;
    this.designerMember = designerMember;
    this.plannerMember = plannerMember;
    this.images = images;
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
      .images(updateTeamRequest.getImages())
      .deleteFileName(updateTeamRequest.getDeleteFileName())
      .build();
  }
  
}
