package kr.joljak.domain.team.dto;

import java.util.List;
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
  private ProjectCategory category;
  private String content;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private List<MultipartFile> images;
  private List<String> deleteFileName;
  
  @Builder
  public UpdateTeam(
    String teamName, ProjectCategory category, String content,
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
  
}
