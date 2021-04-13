package kr.joljak.domain.team.dto;

import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTeam {
  
  private String teamName;
  private ProjectCategory projectCategory;
  private String content;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private MultipartFile file;
  private String deleteFileName;
  
  @Builder
  public UpdateTeam(
    String teamName, ProjectCategory projectCategory, String content,
    String mediaArtMember, String developerMember,
    String designerMember, String plannerMember,
    MultipartFile file, String deleteFileName
  ){
    this.teamName = teamName;
    this.projectCategory = projectCategory;
    this.content = content;
    this.mediaArtMember = mediaArtMember;
    this.developerMember = developerMember;
    this.designerMember = designerMember;
    this.plannerMember = plannerMember;
    this.file = file;
    this.deleteFileName = deleteFileName;
  }
  
}
