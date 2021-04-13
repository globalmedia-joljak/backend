package kr.joljak.domain.team.dto;

import java.util.List;
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
  private ProjectCategory category;

  @Setter
  private User author;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private List<MultipartFile> images;

  @Builder
  public SimpleTeam(
    String teamName, String content, ProjectCategory category,
    String mediaArtMember, String designerMember,
    String developerMember, String plannerMember,
    List<MultipartFile> images
  ) {
    this.teamName = teamName;
    this.content = content;
    this.category = category;
    this.mediaArtMember = mediaArtMember;
    this.designerMember = designerMember;
    this.developerMember = developerMember;
    this.plannerMember = plannerMember;
    this.images = images;
  }

}
