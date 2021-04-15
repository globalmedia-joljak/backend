package kr.joljak.domain.team.dto;

import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class SimpleTeam {
 
  private String teamName;
  private String content;
  private ProjectCategory projectCategory;

  @Setter
  private User author;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private MultipartFile file;

  @Builder
  public SimpleTeam(
    String teamName, String content, ProjectCategory projectCategory,
    String mediaArtMember, String designerMember,
    String developerMember, String plannerMember,
    MultipartFile file
  ) {
    this.teamName = teamName;
    this.content = content;
    this.projectCategory = projectCategory;
    this.mediaArtMember = mediaArtMember;
    this.designerMember = designerMember;
    this.developerMember = developerMember;
    this.plannerMember = plannerMember;
    this.file = file;
  }

}
